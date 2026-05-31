package com.trading.order_matching_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderMatchingEngineApplication {

    public static void main(String[] args) {

          SpringApplication.run(OrderMatchingEngineApplication.class, args);
    }

}

// buyer's price >= seller's price
//buyer's price = he can buy at that max price
// seller's price = he can sell at min that price