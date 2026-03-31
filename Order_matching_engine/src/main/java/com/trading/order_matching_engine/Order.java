package com.trading.order_matching_engine;

import jakarta.persistence.*;

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
    private String status;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String createdAt;
}
