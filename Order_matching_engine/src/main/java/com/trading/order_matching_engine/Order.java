package com.trading.order_matching_engine;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String status = "OPEN";

    @GeneratedValue(strategy = GenerationType.AUTO)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    public static enum OrderType{
        BUY, SELL
    }
}
