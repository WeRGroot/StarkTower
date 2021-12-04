package com.stockbroker.model;

import java.time.LocalDate;
import java.util.UUID;

public class CardPayment extends Payment{

    private String cardNumber;
    private String cardHolderName;
    private LocalDate expiryDate;


    public CardPayment(PaymentType type) {
        super(type, UUID.randomUUID().toString());
    }

    @Override
    public void depositMoney(double amount, Wallet wallet) {
        wallet.addMoney(amount);
    }

    @Override
    public void withdrawMoney(double amount, Wallet wallet) {
        wallet.removeMoney(amount);
    }
}
