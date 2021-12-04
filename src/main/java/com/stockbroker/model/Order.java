package com.stockbroker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private int id;
    private OrderType type;
    private int quantity;
    private Account dematAccount;
    private String symbol;
    private double tradePrice;
}
