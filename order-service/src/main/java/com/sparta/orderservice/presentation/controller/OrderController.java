package com.sparta.orderservice.presentation.controller;

import com.sparta.orderservice.application.dto.OrderRequestDto;
import com.sparta.orderservice.application.dto.OrderResponseDto;
import com.sparta.orderservice.application.service.OrderService;
import com.sparta.orderservice.domain.model.Order;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto requestDto) {
        OrderResponseDto responseDto = orderService.createOrder(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    // 주문 전체 조회
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getALlOrders());
    }
    // 주문 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable("id") UUID id,
            @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
