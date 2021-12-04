package com.stockbroker.model.stockexchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StockExchange implements IStockExchange {

    private HashMap<String, IStockGateway> stockGateways;
    private HashMap<String, Double> stockPrices;
    private HashMap<String, StockOrder> stockOrders;

    public StockExchange() {
        stockGateways = new HashMap<>();
        stockPrices = new HashMap<>();
        stockOrders = new HashMap<>();
    }

    @Override
    public void register(IStockGateway stockGateway) {
        stockGateways.put(stockGateway.getName(), stockGateway);
        System.out.println("StockGateway registered " + stockGateway.getName());
    }

    @Override
    public void unregister(IStockGateway stockGateway) {
        stockGateways.remove(stockGateway.getName());
        System.out.println("StockGateway unregistered " + stockGateway.getName());
    }

    @Override
    public void notifyStockGatewayForOrder(List<ExchangeOrder> exchangeOrderList) {
        if (exchangeOrderList.size() > 0) {
            for (ExchangeOrder order : exchangeOrderList) {
                IStockGateway stockGateway = stockGateways.get(order.getStockGatewayName());
                if (stockGateway == null) {
                    throw new RuntimeException("Invalid stock gateway");
                }
                stockGateway.updateOrder(order);
            }
        }
    }

    @Override
    public void notifyStockGatewayForPrice(ExchangeStock exchangeStock) {
        for (IStockGateway stockGateway : stockGateways.values()) {
            stockGateway.updatePrice(exchangeStock);
        }
    }

    @Override
    public double getPrice(String symbol) {
        return stockPrices.get(symbol);
    }

    @Override
    public long getBuyCount(String symbol) {
        if (!stockOrders.containsKey(symbol)) {
            throw new RuntimeException("Invalid stock symbol");
        }
        return stockOrders.get(symbol).getBuyOrderCount();
    }

    @Override
    public long getSellCount(String symbol) {
        if (!stockOrders.containsKey(symbol)) {
            throw new RuntimeException("Invalid stock symbol");
        }
        return stockOrders.get(symbol).getSellOrderCount();
    }

    @Override
    public void buyStock(ExchangeOrder exchangeOrder) {
        String symbol = exchangeOrder.getSymbol();
        if (!stockOrders.containsKey(symbol)) {
            throw new RuntimeException("Invalid stock symbol");
        }
        StockOrder stockOrder = stockOrders.get(symbol);
        ArrayList<ExchangeOrder> executedOrderList = stockOrder.executeBuyOrder(exchangeOrder);
        notifyStockGatewayForOrder(executedOrderList);
    }

    @Override
    public void sellStock(ExchangeOrder exchangeOrder) {
        String symbol = exchangeOrder.getSymbol();
        if (!stockOrders.containsKey(symbol) && !exchangeOrder.isInitialOrder) {
            throw new RuntimeException("Invalid stock symbol");
        }
        if(exchangeOrder.isInitialOrder()){
            StockOrder stockOrder = new StockOrder(exchangeOrder.getSymbol());
            stockOrder.setSellCount(exchangeOrder.getQuantity());
            stockOrder.addToSellQueue(exchangeOrder);
            stockOrders.put(exchangeOrder.getSymbol(), stockOrder);
            return;
        }

        StockOrder stockOrder = stockOrders.get(symbol);
        ArrayList<ExchangeOrder> executedOrderList = stockOrder.executeSellOrder(exchangeOrder);
        notifyStockGatewayForOrder(executedOrderList);
    }

    @Override
    public void addStock(String symbol, Double price) {
        if(stockPrices.containsKey(symbol)){
            throw new RuntimeException("Stock already present");
        }
        stockPrices.put(symbol, price);
    }

    @Override
    public void updateStockPrice(String symbol, Double price) {
        if(!stockPrices.containsKey(symbol)){
            throw new RuntimeException("Stock not present");
        }
        stockPrices.put(symbol, price);
        notifyStockGatewayForPrice(new ExchangeStock(symbol, price));
    }
}
