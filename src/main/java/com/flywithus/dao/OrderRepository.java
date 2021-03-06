package com.flywithus.dao;

import com.flywithus.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderId(String orderId);

}
