package com.trading.order_matching_engine.repository;

import com.trading.order_matching_engine.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
