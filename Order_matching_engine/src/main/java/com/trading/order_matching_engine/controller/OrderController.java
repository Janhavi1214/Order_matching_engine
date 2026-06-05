package com.trading.order_matching_engine.controller;

import com.trading.order_matching_engine.entity.Order;
import com.trading.order_matching_engine.entity.Trade;
import com.trading.order_matching_engine.repository.OrderRepository;
import com.trading.order_matching_engine.repository.TradeRepository;
import com.trading.order_matching_engine.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value="/orders")
    public List<Trade> save(@Valid @RequestBody Order ord){
        List<Trade> saveOrder = orderService.placeOrder(ord);
        return saveOrder;
    }

    @GetMapping(value = "/orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/trades")
    public  List<Trade> getAllTrades(){
        return orderService.getAllTrades();
    }

}
