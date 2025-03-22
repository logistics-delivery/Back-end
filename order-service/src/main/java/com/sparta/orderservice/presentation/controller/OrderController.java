package com.sparta.orderservice.presentation.controller;

import com.sparta.orderservice.application.dto.OrderRequestDto;
import com.sparta.orderservice.application.dto.OrderResponseDto;
import com.sparta.orderservice.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Order Service", description = "주문 서비스 API")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    // 주문 생성
    @Operation(summary = "Order 등록 ", description = "Order 생성 api 입니다.")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto requestDto) {
        OrderResponseDto responseDto = orderService.createOrder(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    // 주문 전체 조회
    @Operation(summary = "Order 전체 조회 ", description = "Order 전체 조회 api 입니다.")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getALlOrders());
    }
    // 주문 단일 조회
    @Operation(summary = "Order 단일 조회 ", description = "Order 단일 조회 api 입니다.")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // 주문 수정
    @Operation(summary = "Order 수정 ", description = "Order 수정 입니다.")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable("id") UUID id,
            @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderRequestDto));
    }

    // 주문 삭제
    @Operation(summary = "Order 삭제 ", description = "Order 삭제 api 입니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String > deleteOrder(@PathVariable("id") UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("주문이 성공적으로 삭제되었습니다.");
    }

    // 주문 취소
    @Operation(summary = "Order 취소 ", description = "Order 생성 api 입니다.")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(
            @PathVariable("id") UUID id,
            @RequestParam("reason") String cancelReason) {
        return ResponseEntity.ok(orderService.cancelOrder(id, cancelReason));
    }


}
