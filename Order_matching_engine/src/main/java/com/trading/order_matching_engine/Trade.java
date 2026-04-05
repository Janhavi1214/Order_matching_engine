package com.trading.order_matching_engine;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long buyerOrderID;

    @Column(nullable = false)
    private Long sellerOrderID;

    @Column(nullable = false)
    private Double executedPrice;

    @Column(nullable = false)
    private Integer executedQuantity;

    private LocalDateTime executedAt = LocalDateTime.now();
}
