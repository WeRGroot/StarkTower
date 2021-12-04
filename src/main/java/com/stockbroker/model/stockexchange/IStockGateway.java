package com.stockbroker.model.stockexchange;

public interface IStockGateway {
    void update(ExchangeOrder exchangeOrder);
    String getName();
}
