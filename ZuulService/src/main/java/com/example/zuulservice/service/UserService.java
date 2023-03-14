package com.example.zuulservice.service;

import com.example.zuulservice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    Optional<User> getUser(String userName);
    List<User> getAllUsers();
}
