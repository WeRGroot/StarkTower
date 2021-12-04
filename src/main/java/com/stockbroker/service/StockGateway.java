package com.stockbroker.service;

import com.stockbroker.model.Order;
import com.stockbroker.model.OrderType;
import com.stockbroker.model.StockLot;
import com.stockbroker.model.stockexchange.ExchangeOrder;
import com.stockbroker.model.stockexchange.ExchangeStock;
import com.stockbroker.model.stockexchange.IStockExchange;
import com.stockbroker.model.stockexchange.IStockGateway;
import com.stockbroker.repository.StockInventoryRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class StockGateway implements IStockGateway {

    private String name;
    private IStockExchange stockExchange;
    private StockInventoryRepository stockInventoryRepository;

    public StockGateway(String name, IStockExchange exchange) {
        this.name = name;
        this.stockExchange = exchange;
        this.stockInventoryRepository = StockInventoryRepository.getInstance();
    }

    @Override
    public void updateOrder(ExchangeOrder exchangeOrder) {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void updatePrice(ExchangeStock exchangeStock) {
        this.stockInventoryRepository.updatePrice(exchangeStock.getSymbol(), exchangeStock.getCurrPrice());
    }

    public double getPrice(String symbol) {
        return stockExchange.getPrice(symbol);
    }

    public void buyStock(Order order) {
        StockLot stockLot = order.getStockLot();
        if (stockLot == null || stockLot.getQuantity() <= 0) {
            throw new RuntimeException("Invalid order!!");
        }
        ExchangeOrder exchangeOrder = new ExchangeOrder(stockLot.getQuantity(), order.getDematAccount().getId(), order.getStockLot().getStock().getSymbol(), name, OrderType.BUY);
        stockExchange.buyStock(exchangeOrder);
    }

    public void sellStock(Order order) {
        StockLot stockLot = order.getStockLot();
        if (stockLot == null || stockLot.getQuantity() <= 0) {
            throw new RuntimeException("Invalid order!!");
        }
        ExchangeOrder exchangeOrder = new ExchangeOrder(stockLot.getQuantity(), order.getDematAccount().getId(), order.getStockLot().getStock().getSymbol(), name, OrderType.SELL);
        stockExchange.buyStock(exchangeOrder);
    }

}
