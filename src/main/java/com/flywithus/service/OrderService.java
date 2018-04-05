package com.flywithus.service;

import com.flywithus.dao.OrderRepository;
import com.flywithus.dto.FlightTO;
import com.flywithus.dto.OrderTO;
import com.flywithus.dto.OrderTO.OrderBuilder;
import com.flywithus.dto.UserTO;
import com.flywithus.entity.Order;
import com.flywithus.utility.ObjectConverter;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ObjectConverter objectConverter;

    public OrderService(OrderRepository orderRepository, ObjectConverter objectConverter) {
        this.orderRepository = orderRepository;
        this.objectConverter = objectConverter;
    }

    public OrderTO createOrder(FlightTO flight, Integer numberOfTickets) {
        return createOrder(flight, numberOfTickets, null);
    }

    public OrderTO createOrder(FlightTO flight, Integer numberOfTickets, UserTO user) {
        OrderTO orderTO = new OrderBuilder()
                .withFlight(flight)
                .withNumberOfTickets(numberOfTickets)
                .withUser(user)
                .build();
        Order order = objectConverter.convert(orderTO, Order.class);
        orderRepository.save(order);
        return orderTO;
    }
}
