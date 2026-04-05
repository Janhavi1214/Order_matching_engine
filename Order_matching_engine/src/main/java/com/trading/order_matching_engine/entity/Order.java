package com.trading.order_matching_engine.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType type;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.OPEN;

    private LocalDateTime createdAt = LocalDateTime.now();

    public static enum OrderType{
        BUY, SELL
    }

    public static enum OrderStatus{
        OPEN, PARTIALLY_FILLED, FILLED
    }
}
