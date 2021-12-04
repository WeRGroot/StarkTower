package com.stockbroker.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wallet {
    private String id;
    private double balance;

    public Wallet() {
        this.id = UUID.randomUUID().toString();
    }

    public void addMoney(double amount) {
        System.out.println("Money "+amount+" deposited to wallet: "+id);
        balance += amount;
    }

    public void removeMoney(double amount) {
        if (amount > balance) {
            throw new RuntimeException("Insufficient balance");
        }
        balance -= amount;
        System.out.println("Money "+amount+" deposited to wallet: "+id);
    }
}
