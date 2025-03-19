package com.sparta.orderservice.presentation.controller;

import com.sparta.orderservice.application.dto.OrderRequestDto;
import com.sparta.orderservice.application.dto.OrderResponseDto;
import com.sparta.orderservice.application.dto.UpdateOrderRequestDto;
import com.sparta.orderservice.application.service.OrderService;
import com.sparta.orderservice.domain.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class Ordercontroller {

    private final OrderService orderService;

    public Ordercontroller(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto requestDto){
        return ResponseEntity.ok(orderService.createOrder(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getALlOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable UUID id,
            @RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(orderService.updateOrder(id,orderRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
