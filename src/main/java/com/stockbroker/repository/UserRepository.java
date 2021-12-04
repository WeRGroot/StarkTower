package com.stockbroker.repository;

import com.stockbroker.model.User;

import java.util.HashMap;

public class UserRepository {
    private HashMap<String, User> userRepo;

    public void addUser(User user) {
        if (userRepo.containsKey(user.getId())) {
            throw new RuntimeException("User already exists");
        }
        userRepo.put(user.getId(), user);
    }

    public void removeDematAccount(User user) {
        userRepo.remove(user.getId());
    }
}
