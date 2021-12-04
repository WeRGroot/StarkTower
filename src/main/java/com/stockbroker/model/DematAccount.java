package com.stockbroker.model;

import com.stockbroker.model.stockexchange.IStockGateway;
import com.stockbroker.service.StockGateway;
import java.util.UUID;
import lombok.Getter;

import java.util.HashMap;
import java.util.Objects;

public class DematAccount {
    @Getter
    private String id;
    @Getter
    private Wallet wallet;
    @Getter
    private HashMap<String, StockLot> stockHoldings;
    private StockGateway stockGateway;
    @Getter
    private HashMap<String, Watchlist> watchlist;

    public DematAccount(StockGateway stockGateway) {
        this.stockGateway = stockGateway;
        this.id = UUID.randomUUID().toString();
        this.wallet = new Wallet();
        this.stockHoldings = new HashMap<>();
        this.watchlist = new HashMap<>();
    }

    public double getCurrentPL() {
        double totalPL = 0;
        for (String symbol : stockHoldings.keySet()) {
            totalPL += getCurrentPL(symbol);
        }
        return totalPL;
    }

    public double getCurrentPL(String symbol) {
        double currPrice = stockGateway.getPrice(symbol);
        StockLot stockLot = stockHoldings.get(symbol);
        double priceDiff = currPrice - stockLot.getAvgBuyPrice();
        return priceDiff * stockLot.getQuantity();
    }

    public void executeBuy(Order order) {
        stockGateway.buyStock(order);
    }

    public void executeSell(Order order) {
        stockGateway.buyStock(order);
    }

    public void addStock(StockLot newStockLot) {
        Stock newStock = newStockLot.getStock();
        String symbol = newStock.getSymbol();
        StockLot currStockLot = stockHoldings.getOrDefault(symbol, new StockLot(newStock, 0));
        currStockLot.addQuantity(newStockLot.getQuantity());
        stockHoldings.put(symbol, currStockLot);

        System.out.println("DematAccount: "+id+" Stock: "+ newStockLot.getStock().getSymbol() + " added of quantity: "+newStockLot.getQuantity());
    }

    public void removeStock(StockLot newStockLot) {
        Stock newStock = newStockLot.getStock();
        String symbol = newStock.getSymbol();
        StockLot currStockLot = stockHoldings.getOrDefault(symbol, new StockLot(newStock, 0));
        currStockLot.removeQuantity(newStockLot.getQuantity());
        stockHoldings.put(symbol, currStockLot);
        System.out.println("DematAccount: "+id+" Stock: "+ newStockLot.getStock().getSymbol() + " removed of quantity: "+newStockLot.getQuantity());
    }

    public void deleteWatchList(String name) {
        watchlist.remove(name);
    }

    public void addToWatchList(String name, Stock stock) {
        watchlist.getOrDefault(name, new Watchlist(name)).addToStockList(stock);
    }

    public void removeFromWatchList(String name, Stock stock) {
        watchlist.getOrDefault(name, new Watchlist(name)).removeFromStockList(stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DematAccount)) return false;
        DematAccount that = (DematAccount) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
