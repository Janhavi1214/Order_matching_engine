package com.trading.order_matching_engine.service;

import com.trading.order_matching_engine.engine.MatchingEngine;
import com.trading.order_matching_engine.engine.OrderBook;
import com.trading.order_matching_engine.entity.Order;
import com.trading.order_matching_engine.entity.Trade;
import com.trading.order_matching_engine.repository.OrderRepository;
import com.trading.order_matching_engine.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderBook orderBook;

    @Autowired
    private MatchingEngine matchingEngine;

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> placeOrder(Order ord){

        Order savedOrder =  orderRepository.save(ord);
        orderBook.addOrder(savedOrder);

        return matchingEngine.match();

    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Trade> getAllTrades(){
        return tradeRepository.findAll();
    }
}
