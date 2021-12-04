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

    public StockOrder(String symbol) {
        this.symbol = symbol;
        buyCount = 0;
        sellCount = 0;
        buyOrderQueue = new ArrayDeque<>();
        sellOrderQueue = new ArrayDeque<>();
    }

    // No of shares available to buy
    public long getBuyCount() {
        return sellCount;
    }

    // No of shares available to sell
    public long getSellCount() {
        return buyCount;
    }

    public ArrayList<ExchangeOrder> executeBuyOrder(ExchangeOrder order) {
        if (sellCount == 0) {
            buyOrderQueue.addLast(order);
            return new ArrayList<>();
        }
        ArrayList<ExchangeOrder> resultList = new ArrayList<>();
        int quantity = order.getQuantity();
        while (quantity != 0 && sellOrderQueue.size() != 0) {
            ExchangeOrder tempOrder = sellOrderQueue.removeFirst();
            int tempQuantity = tempOrder.getQuantity();
            if (tempQuantity >= quantity) {
                int newQuantity = tempQuantity - quantity;
                ExchangeOrder executedOrder = new ExchangeOrder(quantity, tempOrder.getDematAccountId(), symbol, tempOrder.getStockGatewayName(), tempOrder.getType());
                resultList.add(executedOrder);
                resultList.add(order);
                if (newQuantity > 0) {
                    ExchangeOrder newOrder = new ExchangeOrder(newQuantity, tempOrder.getDematAccountId(), symbol, tempOrder.getStockGatewayName(), tempOrder.getType());
                    sellOrderQueue.addFirst(newOrder);
                }
                quantity = 0;
            } else {
                quantity = quantity - tempQuantity;
                ExchangeOrder executedOrder = new ExchangeOrder(tempQuantity, tempOrder.getDematAccountId(), symbol, tempOrder.getStockGatewayName(), tempOrder.getType());
                resultList.add(executedOrder);
            }
        }
        if (quantity != 0) {
            ExchangeOrder newOrder = new ExchangeOrder(quantity, order.getDematAccountId(), symbol, order.getStockGatewayName(), order.getType());
            buyOrderQueue.addLast(newOrder);
        }
        return resultList;
    }

    public ArrayList<ExchangeOrder> executeSellOrder(ExchangeOrder order) {
        if (buyCount == 0) {
            sellOrderQueue.addLast(order);
            return new ArrayList<>();
        }
        ArrayList<ExchangeOrder> resultList = new ArrayList<>();
        int quantity = order.getQuantity();
        while (quantity != 0 && buyOrderQueue.size() != 0) {
            ExchangeOrder tempOrder = buyOrderQueue.removeFirst();
            int tempQuantity = tempOrder.getQuantity();
            if (tempQuantity >= quantity) {
                int newQuantity = tempQuantity - quantity;
                ExchangeOrder executedOrder = new ExchangeOrder(quantity, tempOrder.getDematAccountId(), symbol, tempOrder.getStockGatewayName(), tempOrder.getType());
                resultList.add(executedOrder);
                resultList.add(order);
                if (newQuantity > 0) {
                    ExchangeOrder newOrder = new ExchangeOrder(newQuantity, tempOrder.getDematAccountId(), symbol, tempOrder.getStockGatewayName(), tempOrder.getType());
                    buyOrderQueue.addFirst(newOrder);
                }
                quantity = 0;
            } else {
                quantity = quantity - tempQuantity;
                ExchangeOrder executedOrder = new ExchangeOrder(tempQuantity, tempOrder.getDematAccountId(), symbol, tempOrder.getStockGatewayName(), tempOrder.getType());
                resultList.add(executedOrder);
            }
        }
        if (quantity != 0) {
            ExchangeOrder newOrder = new ExchangeOrder(quantity, order.getDematAccountId(), symbol, order.getStockGatewayName(), order.getType());
            sellOrderQueue.addLast(newOrder);
        }
        return resultList;
    }

    public void addToSellQueue(ExchangeOrder order){
        sellOrderQueue.addLast(order);
    }
}
