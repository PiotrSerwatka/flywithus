package com.flywithus.data;

import com.flywithus.dto.FlightTO;
import com.flywithus.dto.OrderTO;
import com.flywithus.dto.OrderTO.OrderBuilder;
import com.flywithus.entity.Order;
import com.flywithus.type.OrderStatus;

public class TestOrder {

    public static Order createOrderEntity(Integer numberOfTickets, OrderStatus orderStatus) {
        Order order = new Order();
        order.setNumberOfTickets(numberOfTickets);
        order.setOrderStatus(orderStatus);
        return order;
    }

    public static OrderTO createOrder(Integer numberOfTickets, FlightTO flightTO) {
        OrderTO orderTO = new OrderBuilder()
                .withFlight(flightTO)
                .withNumberOfTickets(numberOfTickets)
                .build();
        return orderTO;
    }

}
