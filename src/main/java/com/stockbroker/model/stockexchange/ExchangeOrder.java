package com.stockbroker.model.stockexchange;

import com.stockbroker.model.OrderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeOrder {
    private int id;
    private int quantity;
    private int dematAccountId;
    private String symbol;
    private String stockGatewayName;
    private OrderType type;
}
