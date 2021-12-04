package com.stockbroker.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Payment {
    private PaymentType type;
    private String id;

    public abstract void depositMoney(double amount, Wallet wallet);

    public abstract void withdrawMoney(double amount, Wallet wallet);
}
