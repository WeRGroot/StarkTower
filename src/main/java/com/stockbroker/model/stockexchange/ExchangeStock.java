package com.stockbroker.model.stockexchange;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExchangeStock {
    private String symbol;
    private double currPrice;
}
