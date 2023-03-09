package com.example.microservice1.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MicroService2")
public interface MicroService2Client {
    @GetMapping("/")
    public String TestEndPoint();
}
