package com.stockbroker.model.stockexchange;

public interface IStockGateway {
    void updateOrder(ExchangeOrder exchangeOrder);
    String getName();
    void updatePrice(ExchangeStock exchangeStock);
}
