package com.trading.order_matching_engine.engine;

import com.trading.order_matching_engine.entity.Order;
import com.trading.order_matching_engine.entity.Trade;
import com.trading.order_matching_engine.engine.OrderBook;
import com.trading.order_matching_engine.repository.OrderRepository;
import com.trading.order_matching_engine.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingEngine {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private TradeRepository tradeRepo;

    @Autowired
    private OrderBook orderBook;

    public List<Trade> match(){

        List<Trade> executedTrades = new ArrayList<>();

        while(orderBook.hasBuyOrders() && orderBook.hasSellOrders()){

            Order bestBuy = orderBook.peekBestBuy();
            Order bestSell = orderBook.peekBestSell();

            if(bestBuy.getPrice() < bestSell.getPrice()){
                break;
            }
            else{
                Order matchedBuy = orderBook.pollBestBuy();
                Order matchedSell = orderBook.pollBestSell();

                int tradedQuantity = Math.min(matchedBuy.getQuantity(), matchedSell.getQuantity());

                Trade trade = new Trade();
                trade.setBuyerOrderID(matchedBuy.getId());
                trade.setSellerOrderID(matchedSell.getId());
                trade.setExecutedQuantity(tradedQuantity);
                trade.setExecutedPrice(matchedSell.getPrice());

                if(tradedQuantity == matchedBuy.getQuantity()){
                    matchedBuy.setStatus(Order.OrderStatus.FILLED);
                }
                else if(tradedQuantity < matchedBuy.getQuantity()){

                    int remainingQuantity = matchedBuy.getQuantity() - tradedQuantity;
                    matchedBuy.setQuantity(remainingQuantity);
                    matchedBuy.setStatus(Order.OrderStatus.PARTIALLY_FILLED);
                    orderBook.addOrder(matchedBuy);
                }

                if(tradedQuantity == matchedSell.getQuantity()){
                    matchedSell.setStatus(Order.OrderStatus.FILLED);
                }
                else if(tradedQuantity < matchedSell.getQuantity()){

                    int remainingQuantity = matchedSell.getQuantity() - tradedQuantity;
                    matchedSell.setQuantity(remainingQuantity);
                    matchedSell.setStatus(Order.OrderStatus.PARTIALLY_FILLED);
                    orderBook.addOrder(matchedSell);
                }

                tradeRepo.save(trade);
                orderRepo.save(matchedBuy);
                orderRepo.save(matchedSell);
                executedTrades.add(trade);
            }
        }

        return executedTrades;
    }
}
