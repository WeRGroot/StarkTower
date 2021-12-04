package com.stockbroker.model.stockexchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class StockExchange implements IStockExchange{

    private HashMap<String, IStockGateway> stockGateways;
    private HashMap<String, Double> stockPrices;
    private HashMap<String, StockOrder> stockOrders;

    @Override
    public void register(IStockGateway stockGateway) {
        stockGateways.put(stockGateway.getName(), stockGateway);
        System.out.println("StockGateway registered "+stockGateway.getName());
    }

    @Override
    public void unregister(IStockGateway stockGateway) {
        stockGateways.remove(stockGateway.getName());
        System.out.println("StockGateway unregistered "+stockGateway.getName());
    }

    @Override
    public void notifyStockGateway(ExchangeOrder exchangeOrder) {
        IStockGateway stockGateway = stockGateways.get(exchangeOrder.getStockGatewayName());
        if(stockGateway == null){
            throw new RuntimeException("Invalid stock gateway");
        }
        stockGateway.update(exchangeOrder);
    }

    public double getPrice(String symbol){
        return stockPrices.get(symbol);
    }

    public long getBuyCount(String symbol){
        if(!stockOrders.containsKey(symbol)){
            throw new RuntimeException("Invalid stock symbol");
        }
        return stockOrders.get(symbol).getBuyCount();
    }

    public long getSellCount(String symbol){
        if(!stockOrders.containsKey(symbol)){
            throw new RuntimeException("Invalid stock symbol");
        }
        return stockOrders.get(symbol).getSellCount();
    }

    public void buyStock(ExchangeOrder exchangeOrder){
        String symbol = exchangeOrder.getSymbol();
        if(!stockOrders.containsKey(symbol)){
            throw new RuntimeException("Invalid stock symbol");
        }
        StockOrder stockOrder = stockOrders.get(symbol);
        ArrayList<ExchangeOrder> executedOrderList = stockOrder.executeBuyOrder(exchangeOrder);
        if(executedOrderList.size() > 0){
            for(ExchangeOrder order : executedOrderList){
                notifyStockGateway(order);
            }
        }
    }

    public void sellStock(ExchangeOrder exchangeOrder){
        String symbol = exchangeOrder.getSymbol();
        if(!stockOrders.containsKey(symbol)){
            throw new RuntimeException("Invalid stock symbol");
        }
        StockOrder stockOrder = stockOrders.get(symbol);
        ArrayList<ExchangeOrder> executedOrderList = stockOrder.executeBuyOrder(exchangeOrder);
        if(executedOrderList.size() > 0){
            for(ExchangeOrder order : executedOrderList){
                notifyStockGateway(order);
            }
        }
    }
}
