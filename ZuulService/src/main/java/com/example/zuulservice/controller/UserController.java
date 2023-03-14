package com.example.zuulservice.controller;

import com.example.zuulservice.model.User;
import com.example.zuulservice.security.JWTUtil;
import com.example.zuulservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;

    //change void
    @PostMapping("/signup")
    public void signUp(@RequestBody User user){
        System.out.println("-----------------------------------");
        try{
            userService.addUser(user);
        }catch (Exception e){
            System.out.println("invalid data");
        }

//        System.out.println(userService.getUser(user.getUsername()).get().toString());
    }
    @PostMapping("/login")
    public String logIn(@RequestBody User user){
        Optional<User> optionalUser = userService.getUser(user.getUsername());
        if(optionalUser.isPresent()){
            if(optionalUser.get().getPassword().equals(user.getPassword())){
                return jwtUtil.getAccessToken(user);
            }else{
                return "Worng Password";
            }
        }else{
            return "There is no User called "+user.getUsername();
        }
    }
}
