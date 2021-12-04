package com.stockbroker.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Payment {
    private PaymentType type;
    private String id;
    private double amount;

    abstract void depositMoney(double amount, Wallet wallet);

    abstract void withdrawMoney(double amount, Wallet wallet);
}
