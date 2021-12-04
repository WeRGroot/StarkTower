package com.stockbroker.repository;

import com.stockbroker.model.Stock;

import java.util.HashMap;
import java.util.Map;

public class StockInventoryRepository {
    private static StockInventoryRepository repository;
    private final Map<String, Stock> stockInventory;

    private StockInventoryRepository() {
        this.stockInventory = new HashMap<>();
    }

    public static StockInventoryRepository getInstance() {
        if (repository == null)
            repository = new StockInventoryRepository();
        return repository;
    }

    public void addStock(Stock stock) {
        if (stockInventory.containsKey(stock.getSymbol())) {
            throw new RuntimeException("Stock already present");
        }
        stockInventory.put(stock.getSymbol(), stock);
    }

    public void updatePrice(String symbol, double price) {
        if (!stockInventory.containsKey(symbol)) {
            addStock(new Stock(symbol, price));
        }
        stockInventory.get(symbol).setCurrPrice(price);
    }

    public Stock getStock(String symbol){
        return stockInventory.get(symbol);
    }
}
