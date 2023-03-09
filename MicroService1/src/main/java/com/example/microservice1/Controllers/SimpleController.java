package com.example.microservice1.Controllers;

import com.example.microservice1.Clients.MicroService2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    @Autowired
    MicroService2Client microService2Client;
    @GetMapping("/")
    public String TestEndPoint(){
        return "i'm MicroService1";
    }
    @GetMapping("/x")
    public String TestEndPoint3(){
        return "OK";
    }

    @GetMapping("/M1ConsumeM2")
    public String TestEndPoint2(){
        return microService2Client.TestEndPoint() + " from MicroService1";
    }
}
