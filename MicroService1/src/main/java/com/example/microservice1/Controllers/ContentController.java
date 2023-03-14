package com.example.microservice1.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ContentController {
    @PostMapping("/getAll")
    public void getAll(){}

    @PostMapping("/forUser")
    public void forUser(){}

    @PostMapping("/forMod")
    public void forMod(){}

    @PostMapping("/forAdmin")
    public void forAdmin(){}
}
