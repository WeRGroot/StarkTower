package com.stockbroker.model.stockexchange;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOrder {
    private String symbol;
    private long buyCount;
    private long sellCount;
    private ArrayDeque<ExchangeOrder> buyOrderQueue;
    private ArrayDeque<ExchangeOrder> sellOrderQueue;

    // No of shares available to buy
    public long getBuyCount(){
        return sellCount;
    }

    // No of shares available to sell
    public long getSellCount(){
        return buyCount;
    }

    public ArrayList<ExchangeOrder> executeBuyOrder(ExchangeOrder order){
        if(sellCount == 0){
            buyOrderQueue.addLast(order);
            return new ArrayList<>();
        }

        return null;
    }

    public Optional<ExchangeOrderPair> executeSellOrder(ExchangeOrder order){
        return null;
    }
}
