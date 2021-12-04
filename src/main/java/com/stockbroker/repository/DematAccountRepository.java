package com.stockbroker.repository;

import com.stockbroker.model.DematAccount;

import java.util.HashMap;

public class DematAccountRepository {
    private HashMap<String, DematAccount> dematAccountRepo;

    public void addDematAccount(DematAccount dematAccount) {
        if (dematAccountRepo.containsKey(dematAccount.getId())) {
            throw new RuntimeException("Demat account already exists");
        }
        dematAccountRepo.put(dematAccount.getId(), dematAccount);
    }

    public void removeDematAccount(DematAccount dematAccount) {
        dematAccountRepo.remove(dematAccount.getId());
    }
}
