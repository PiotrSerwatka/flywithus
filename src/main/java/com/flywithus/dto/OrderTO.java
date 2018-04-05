package com.flywithus.dto;

import com.flywithus.type.OrderStatus;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class OrderTO {

    private String orderId;
    private UserTO user;
    private FlightTO flight;
    private Integer numberOfTickets;
    private OrderStatus orderStatus;
    private BigDecimal value;

    private OrderTO() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public UserTO getUser() {
        return user;
    }

    public void setUser(UserTO user) {
        this.user = user;
    }

    public FlightTO getFlight() {
        return flight;
    }

    public void setFlight(FlightTO flight) {
        this.flight = flight;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getValue() {
        return value;
    }

    private void calculateOrderValue() {
        BigDecimal seatPrice = flight.getSeatPrice();
        value = seatPrice.multiply(BigDecimal.valueOf(numberOfTickets));
        Optional.ofNullable(user).ifPresent(user ->
            value = value.subtract(value.multiply(BigDecimal.valueOf(5)).divide(new BigDecimal(100)))
        );
    }

    public static class OrderBuilder {
        private UserTO user;
        private FlightTO flight;
        private Integer numberOfTickets;

        public OrderBuilder withUser(UserTO user) {
            this.user = user;
            return this;
        }

        public OrderBuilder withFlight(FlightTO flight) {
            this.flight = flight;
            return this;
        }

        public OrderBuilder withNumberOfTickets(Integer numberOfTickets) {
            this.numberOfTickets = numberOfTickets;
            return this;
        }

        public OrderTO build() {
            OrderTO orderTO = new OrderTO();
            orderTO.setOrderId(UUID.randomUUID().toString());
            orderTO.setUser(user);
            orderTO.setFlight(flight);
            orderTO.setOrderStatus(OrderStatus.PENDING);
            orderTO.setNumberOfTickets(numberOfTickets);
            orderTO.setOrderStatus(OrderStatus.PENDING);
            orderTO.calculateOrderValue();
            return orderTO;
        }
    }
}
