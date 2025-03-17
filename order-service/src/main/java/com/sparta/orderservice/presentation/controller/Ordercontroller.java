package com.sparta.orderservice.presentation.controller;

import com.sparta.orderservice.application.dto.OrderRequestDto;
import com.sparta.orderservice.application.service.OrderService;
import com.sparta.orderservice.domain.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class Ordercontroller {

    private final OrderService orderService;

    public Ordercontroller(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/test")
    public String getOrder(){
        return "Hello world";
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto requestDto){
        return ResponseEntity.ok(orderService.createOrder(requestDto));
    }

}
