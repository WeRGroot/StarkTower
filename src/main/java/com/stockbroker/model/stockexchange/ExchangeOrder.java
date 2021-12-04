package com.stockbroker.model.stockexchange;

import com.stockbroker.model.OrderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeOrder {
    private int id;
    private int quantity;
    private String dematAccountId;
    private String symbol;
    private String stockGatewayName;
    private OrderType type;
    boolean isInitialOrder;

    public ExchangeOrder(int quantity, String dematAccountId, String symbol, String stockGatewayName, OrderType type) {
        this.quantity = quantity;
        this.dematAccountId = dematAccountId;
        this.symbol = symbol;
        this.stockGatewayName = stockGatewayName;
        this.type = type;
        this.isInitialOrder = isInitialOrder;
    }
}
