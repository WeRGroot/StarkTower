package com.stockbroker.repository;

import com.stockbroker.model.DematAccount;

import java.util.HashMap;

public class DematAccountRepository {
    private HashMap<String, DematAccount> dematAccountRepo = new HashMap<>();
    private static DematAccountRepository dematAccountRepository = null;

    public static DematAccountRepository getInstance(){
        if(dematAccountRepository == null){
            dematAccountRepository = new DematAccountRepository();
        }

        return dematAccountRepository;
    }


    public void addDematAccount(DematAccount dematAccount) {
        if (dematAccountRepo.containsKey(dematAccount.getId())) {
            throw new RuntimeException("Demat account already exists");
        }
        dematAccountRepo.put(dematAccount.getId(), dematAccount);
    }

    public void removeDematAccount(DematAccount dematAccount) {
        dematAccountRepo.remove(dematAccount.getId());
    }

    public DematAccount getDematAccount(String id) {
        return dematAccountRepo.get(id);
    }
}
