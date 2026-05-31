package com.trading.order_matching_engine.entity;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyerOrderID() {
        return buyerOrderID;
    }

    public void setBuyerOrderID(Long buyerOrderID) {
        this.buyerOrderID = buyerOrderID;
    }

    public Long getSellerOrderID() {
        return sellerOrderID;
    }

    public void setSellerOrderID(Long sellerOrderID) {
        this.sellerOrderID = sellerOrderID;
    }

    public Double getExecutedPrice() {
        return executedPrice;
    }

    public void setExecutedPrice(Double executedPrice) {
        this.executedPrice = executedPrice;
    }

    public Integer getExecutedQuantity() {
        return executedQuantity;
    }

    public void setExecutedQuantity(Integer executedQuantity) {
        this.executedQuantity = executedQuantity;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}
