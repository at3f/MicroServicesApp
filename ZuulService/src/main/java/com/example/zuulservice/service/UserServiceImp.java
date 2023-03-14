package com.example.zuulservice.service;

import com.example.zuulservice.model.User;
import com.example.zuulservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
