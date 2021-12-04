package com.stockbroker.model.stockexchange;

import com.stockbroker.model.Stock;
import java.util.List;

public interface IStockExchange {
    void register(IStockGateway stockGateway);

    void unregister(IStockGateway stockGateway);

    void notifyStockGatewayForOrder(List<ExchangeOrder> exchangeOrderList);

    void notifyStockGatewayForPrice(ExchangeStock exchangeStock);

    double getPrice(String symbol);

    long getBuyCount(String symbol);

    long getSellCount(String symbol);

    void buyStock(ExchangeOrder exchangeOrder);

    void sellStock(ExchangeOrder exchangeOrder);

    void addStock(String symbol, Double price);

    void updateStockPrice(String symbol, Double price);
}
