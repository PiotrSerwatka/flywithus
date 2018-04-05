package com.flywithus.integration;

import com.flywithus.BaseTest;
import com.flywithus.dao.OrderRepository;
import com.flywithus.data.TestAirport;
import com.flywithus.data.TestUser;
import com.flywithus.dto.FlightTO;
import com.flywithus.dto.OrderTO;
import com.flywithus.dto.UserTO;
import com.flywithus.entity.Order;
import com.flywithus.payment.PaymentProvider;
import com.flywithus.service.FlightService;
import com.flywithus.service.OrderService;
import com.flywithus.service.PaymentService;
import com.flywithus.service.UserService;
import com.flywithus.type.OrderStatus;
import com.flywithus.type.PaymentStatus;
import com.flywithus.utility.search.FlightSearchData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class BookFlightIntegrationTest extends BaseTest {

    @Autowired
    private FlightService flightService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentProvider paymentProvider;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:database/integration/bookFlightForUnregisteredUserScript.sql"),
            @Sql(scripts = "classpath:database/clean.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldBookFlightForUnregisteredUser() {
        //given
        FlightSearchData flightSearchData = getFlightSearchData();
        given(paymentProvider.createPayment(any(), any(), any())).willReturn(PaymentStatus.SUCCESS);

        //when
        FlightTO flight = flightService.findFlights(flightSearchData).stream().findFirst().orElseThrow(() -> new RuntimeException("Could not find flight"));
        OrderTO order = orderService.createOrder(flight, flightSearchData.getNumberOfSeats());
        PaymentStatus paymentStatus = paymentService.processPaymentForUnregisteredUser(order, "fake-credit-card-number");

        //then
        assertThat(paymentStatus).isEqualTo(PaymentStatus.SUCCESS);
        Optional<Order> orderEntity = orderRepository.findAll().stream().findFirst();
        assertThat(orderEntity.isPresent()).isTrue();
        orderEntity.ifPresent(o -> assertThat(o.getOrderStatus()).isEqualTo(OrderStatus.PAID));
        orderEntity.ifPresent(o -> assertThat(o.getValue()).isEqualTo(new BigDecimal("4.00")));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:database/integration/bookFlightForUnregisteredUserScript.sql"),
            @Sql(scripts = "classpath:database/clean.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldBookFlightForRegisteredUser() {
        //given
        UserTO user = TestUser.createUser();
        FlightSearchData flightSearchData = getFlightSearchData();
        given(paymentProvider.createPayment(any(), any(), any())).willReturn(PaymentStatus.SUCCESS);

        //when
        userService.registerUser(user);
        FlightTO flight = flightService.findFlights(flightSearchData).stream().findFirst().orElseThrow(() -> new RuntimeException("Could not find flight"));
        OrderTO order = orderService.createOrder(flight, flightSearchData.getNumberOfSeats(), user);
        PaymentStatus paymentStatus = paymentService.processPaymentForRegisteredUser(order);

        //then
        assertThat(paymentStatus).isEqualTo(PaymentStatus.SUCCESS);
        Optional<Order> orderEntity = orderRepository.findAll().stream().findFirst();
        assertThat(orderEntity.isPresent()).isTrue();
        orderEntity.ifPresent(o -> assertThat(o.getOrderStatus()).isEqualTo(OrderStatus.PAID));
        orderEntity.ifPresent(o -> assertThat(o.getValue()).isEqualTo(new BigDecimal("3.80")));
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:database/integration/bookFlightForUnregisteredUserScript.sql"),
            @Sql(scripts = "classpath:database/clean.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldNotBookFlightIfPaymentFail() {
        //given
        UserTO user = TestUser.createUser();
        FlightSearchData flightSearchData = getFlightSearchData();
        given(paymentProvider.createPayment(any(), any(), any())).willReturn(PaymentStatus.FAIL);

        //when
        userService.registerUser(user);
        FlightTO flight = flightService.findFlights(flightSearchData).stream().findFirst().orElseThrow(() -> new RuntimeException("Could not find flight"));
        OrderTO order = orderService.createOrder(flight, flightSearchData.getNumberOfSeats(), user);
        PaymentStatus paymentStatus = paymentService.processPaymentForRegisteredUser(order);

        //then
        assertThat(paymentStatus).isEqualTo(PaymentStatus.FAIL);
        Optional<Order> orderEntity = orderRepository.findAll().stream().findFirst();
        assertThat(orderEntity.isPresent()).isTrue();
        orderEntity.ifPresent(o -> assertThat(o.getOrderStatus()).isEqualTo(OrderStatus.PENDING));
    }

    private FlightSearchData getFlightSearchData() {
        FlightSearchData flightSearchData = new FlightSearchData();
        flightSearchData.setFromDate(LocalDateTime.of(2018, 1, 4, 12, 59));
        flightSearchData.setToDate(LocalDateTime.of(2018, 1, 4, 13, 1));
        flightSearchData.setFromAirport(TestAirport.createFromAirport("Wroclaw"));
        flightSearchData.setToAirport(TestAirport.createFromAirport("Poznan"));
        flightSearchData.setNumberOfSeats(2);
        return flightSearchData;
    }

}
