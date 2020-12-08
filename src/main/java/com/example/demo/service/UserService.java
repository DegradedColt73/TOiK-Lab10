package com.example.demo.service;

import com.example.demo.lockedAccountException;
import com.example.demo.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public boolean checkLogin(String login, String password) throws lockedAccountException {
        return this.userRepository.checkLogin(login, password);
    }

}
