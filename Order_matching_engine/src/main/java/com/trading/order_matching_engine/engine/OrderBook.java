package com.trading.order_matching_engine.engine;

import com.trading.order_matching_engine.entity.Order;

import java.util.Collections;
import java.util.PriorityQueue;

public class OrderBook {

    PriorityQueue<Order> buyOrder = new PriorityQueue<>((a,b) -> Double.compare(b.getPrice(), a.getPrice()));
    PriorityQueue<Order> sellOrder = new PriorityQueue<>((a,b) -> Double.compare(a.getPrice(), b.getPrice()));

    public void addOrder(Order order){
        if(order.getType() == Order.OrderType.BUY){
            buyOrder.add(order);
        }
        else{
            sellOrder.add(order);
        }
    }

    public Order peekBestBuy(){
        return buyOrder.peek();
    }

    public Order peekBestSell(){
        return sellOrder.peek();
    }

    public Order pollBestBuy(){
        return buyOrder.poll();
    }

    public Order pollBestSell(){
        return sellOrder.poll();
    }

    public boolean hasBuyOrders(){
        if(buyOrder.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean hasSellOrders(){
        if(sellOrder.isEmpty()){
            return false;
        }
        return true;
    }


}
