package com.flywithus.payment;

import com.flywithus.type.PaymentStatus;

import java.math.BigDecimal;

public interface PaymentProvider {

    PaymentStatus createPayment(String creditCardNumber, String accountNumber, BigDecimal amount);

}
