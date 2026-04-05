package com.trading.order_matching_engine.repository;

import com.trading.order_matching_engine.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
