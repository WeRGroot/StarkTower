package com.stockbroker.repository;

import com.stockbroker.model.User;
import java.util.HashMap;

public class UserRepository {
    private HashMap<String, User> userRepo = new HashMap<>();

    private static UserRepository userRepository = null;

    public static UserRepository getInstance(){
        if(userRepository == null){
            userRepository = new UserRepository();
        }

        return userRepository;
    }

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
