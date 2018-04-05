package com.flywithus.service;

import com.flywithus.BaseTest;
import com.flywithus.dao.OrderRepository;
import com.flywithus.data.TestFlight;
import com.flywithus.dto.FlightTO;
import com.flywithus.dto.OrderTO;
import com.flywithus.entity.Order;
import com.flywithus.utility.ObjectConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;


public class OrderServiceTest extends BaseTest {

    @Autowired
    private ObjectConverter objectConverter;

    private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    private OrderService orderService;

    @Before
    public void beforeTest() {
        orderService = new OrderService(orderRepository, objectConverter);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:database/order/createOrderScript.sql"),
            @Sql(scripts = "classpath:database/clean.sql", executionPhase = AFTER_TEST_METHOD)
    })
    public void shouldCreateOrderForUnregisteredUser() {
        //given
        FlightTO flight = TestFlight.createFlight();
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        //when
        OrderTO order = orderService.createOrder(flight, 2);

        //then
        verify(orderRepository, times(1)).save(orderCaptor.capture());
        Order orderEntity = orderCaptor.getValue();
        assertThat(orderEntity.getUser()).isNull();
        assertThat(orderEntity.getFlight())
                .extracting("fromAirport.name", "toAirport.name")
                .contains("Wroclaw", "Poznan");
        assertThat(orderEntity.getValue()).isEqualTo(order.getValue());
        assertThat(orderEntity.getNumberOfTickets()).isEqualTo(order.getNumberOfTickets());
        assertThat(orderEntity.getOrderStatus()).isEqualTo(order.getOrderStatus());
    }

}