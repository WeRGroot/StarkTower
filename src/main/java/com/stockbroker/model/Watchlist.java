package com.stockbroker.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.UUID;

@Getter
@Setter
public class Watchlist {
    private String id;
    private String name;
    private HashSet<Stock> stockList;

    public Watchlist(String name) {
        this.name = name;
        this.stockList = new HashSet<>();
        this.id = UUID.randomUUID().toString();
    }

    public void addToStockList(Stock stock) {
        stockList.add(stock);
    }

    public void removeFromStockList(Stock stock) {
        stockList.remove(stock);
    }
}
