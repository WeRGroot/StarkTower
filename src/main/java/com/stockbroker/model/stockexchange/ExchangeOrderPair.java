package com.stockbroker.model.stockexchange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeOrderPair {
    private ExchangeOrder buyExchangeOrder;
    private ExchangeOrder sellExchangeOrder;
}
