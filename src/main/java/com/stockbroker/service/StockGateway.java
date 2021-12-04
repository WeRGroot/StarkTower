package com.stockbroker.service;

import com.stockbroker.model.DematAccount;
import com.stockbroker.model.Order;
import com.stockbroker.model.OrderType;
import com.stockbroker.model.Stock;
import com.stockbroker.model.StockLot;
import com.stockbroker.model.stockexchange.ExchangeOrder;
import com.stockbroker.model.stockexchange.ExchangeStock;
import com.stockbroker.model.stockexchange.IStockExchange;
import com.stockbroker.model.stockexchange.IStockGateway;
import com.stockbroker.repository.DematAccountRepository;
import com.stockbroker.repository.StockInventoryRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class StockGateway implements IStockGateway {

    private String name;
    private IStockExchange stockExchange;

    private DematAccountRepository dematAccountRepository = DematAccountRepository.getInstance();
    private StockInventoryRepository stockInventoryRepository;

    public StockGateway(String name, IStockExchange exchange) {
        this.name = name;
        this.stockExchange = exchange;
        this.stockInventoryRepository = StockInventoryRepository.getInstance();
    }

    @Override
    public void updateOrder(ExchangeOrder exchangeOrder) {
        DematAccount dematAccount = dematAccountRepository.getDematAccount(exchangeOrder.getDematAccountId());
        StockLot stockLot = new StockLot(stockInventoryRepository.getStock(exchangeOrder.getSymbol()), exchangeOrder.getQuantity());

        switch (exchangeOrder.getType()){
            case BUY:
                dematAccount.addStock(stockLot);
                dematAccount.getWallet().removeMoney(stockLot.getAvgBuyPrice() * stockLot.getQuantity());
                break;
            case SELL:
                dematAccount.removeStock(stockLot);
                dematAccount.getWallet().addMoney(stockLot.getAvgBuyPrice() * stockLot.getQuantity());
        }
    }

    @Override
    public String getName() {
        return name;
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
