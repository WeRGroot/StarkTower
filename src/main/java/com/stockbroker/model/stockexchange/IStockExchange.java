package com.stockbroker.model.stockexchange;

public interface IStockExchange {
    void register(IStockGateway stockGateway);
    void unregister(IStockGateway stockGateway);
    void notifyStockGateway(ExchangeOrder exchangeOrder);
}
