package com.stockbroker.model;

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
        accountList = new HashSet<>();
    }

    public void addDematAccount(DematAccount dematAccount) {
        accountList.add(dematAccount);
    }

    public void removeDematAccount(DematAccount dematAccount) {
        accountList.remove(dematAccount);
    }
}
