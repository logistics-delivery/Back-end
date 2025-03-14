package com.sparta.orderservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class Ordercontroller {

    @GetMapping("/test")
    public String getOrder(){
        return "Hello world";
    }

}
