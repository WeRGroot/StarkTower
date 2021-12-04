package com.stockbroker.model;

import java.time.LocalDate;

public class CardPayment extends Payment{

    private String cardNumber;
    private String cardHolderName;
    private LocalDate expiryDate;


    public CardPayment(PaymentType type, String id, double amount) {
        super(type, id, amount);
    }

    @Override
    void depositMoney(double amount, Wallet wallet) {
        wallet.addMoney(amount);
    }

    @Override
    void withdrawMoney(double amount, Wallet wallet) {
        wallet.removeMoney(amount);
    }
}
