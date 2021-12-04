package com.stockbroker.repository;

import com.stockbroker.model.Wallet;

import java.util.HashMap;

public class WalletRepository {
    private HashMap<String, Wallet> walletRepo;

    public void addDematAccount(Wallet wallet) {
        if (walletRepo.containsKey(wallet.getId())) {
            throw new RuntimeException("Wallet already exists");
        }
        walletRepo.put(wallet.getId(), wallet);
    }

    public void removeDematAccount(Wallet wallet) {
        walletRepo.remove(wallet.getId());
    }
}