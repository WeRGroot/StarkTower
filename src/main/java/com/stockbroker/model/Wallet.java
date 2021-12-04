package com.stockbroker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wallet {
    private String id;
    private double balance;

    public void addMoney(double amount) {
        balance += amount;
    }

    public void removeMoney(double amount) {
        if (amount > balance) {
            throw new RuntimeException("Insufficient balance");
        }
        balance += amount;
    }
}
