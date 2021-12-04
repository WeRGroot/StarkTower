package com.stockbroker.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
public class User {
    private final HashSet<DematAccount> accountList;
    private String id;
    private String name;
    private String email;
    private String phone;

    public User() {
        this.id = UUID.randomUUID().toString();
        accountList = new HashSet<>();
    }

    public void addDematAccount(DematAccount dematAccount) {
        accountList.add(dematAccount);
    }

    public void removeDematAccount(DematAccount dematAccount) {
        accountList.remove(dematAccount);
    }
}
