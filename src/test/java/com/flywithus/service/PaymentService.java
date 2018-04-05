package com.flywithus.service;

import com.flywithus.dao.OrderRepository;
import com.flywithus.dto.OrderTO;
import com.flywithus.entity.Order;
import com.flywithus.payment.PaymentProvider;
import com.flywithus.type.OrderStatus;
import com.flywithus.type.PaymentStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentProvider paymentProvider;
    private OrderRepository orderRepository;
    private String bankAccount;

    public PaymentService(@Value("service.bank.account") String bankAccount, PaymentProvider paymentProvider, OrderRepository orderRepository) {
        this.bankAccount = bankAccount;
        this.paymentProvider = paymentProvider;
        this.orderRepository = orderRepository;
    }

    public PaymentStatus processPaymentForUnregisteredUser(OrderTO order, String creditCardNumber) {
        PaymentStatus paymentStatus = paymentProvider.createPayment(creditCardNumber, bankAccount, order.getValue());
        Order orderEntity = orderRepository.findByOrderId(order.getOrderId());
        if (paymentStatus == PaymentStatus.SUCCESS) {
            orderEntity.setOrderStatus(OrderStatus.PAID);
        }
        return paymentStatus;
    }

    public PaymentStatus processPaymentForRegisteredUser(OrderTO order) {
        PaymentStatus paymentStatus = paymentProvider.createPayment(order.getUser().getCreditCardNumber(), bankAccount, order.getValue());
        Order orderEntity = orderRepository.findByOrderId(order.getOrderId());
        if (paymentStatus == PaymentStatus.SUCCESS) {
            orderEntity.setOrderStatus(OrderStatus.PAID);
        }
        return paymentStatus;
    }

}
