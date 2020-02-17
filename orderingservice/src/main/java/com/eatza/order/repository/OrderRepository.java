package com.eatza.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eatza.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
