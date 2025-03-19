package com.sparta.orderservice.application.service;

import com.sparta.orderservice.application.dto.OrderRequestDto;
import com.sparta.orderservice.application.dto.OrderResponseDto;
import com.sparta.orderservice.domain.model.Order;
import com.sparta.orderservice.domain.model.OrderStatus;
import com.sparta.orderservice.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    @Transactional
    public Order createOrder(OrderRequestDto requestDto){
        Order order = Order.builder()
                .name(requestDto.getName())
                .supplierId(requestDto.getSupplierId())
                .receiverId(requestDto.getReceiverId())
                .productId(requestDto.getProductId())
                .totalPrice(requestDto.getTotalPrice())
                .requestDetail(requestDto.getRequestDetail())
                .createdBy(requestDto.getCreateBy())
                .build();
                return orderRepository.save(order);
    }


    @Transactional(readOnly = true)
    public List<OrderResponseDto> getALlOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> new OrderResponseDto(
                        order.getOrderId(),
                        order.getName(),
                        order.getSupplierId(),
                        order.getReceiverId(),
                        order.getProductId(),
                        order.getTotalPrice(),
                        order.getStatus(),
                        order.getRequestDetail(),
                        order.getCreatedAt(),
                        order.getCreatedBy()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(UUID orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order Not found" + orderId));
        return new OrderResponseDto(
                order.getOrderId(),
                order.getName(),
                order.getSupplierId(),
                order.getReceiverId(),
                order.getProductId(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getRequestDetail(),
                order.getCreatedAt(),
                order.getCreatedBy()
                );
    }

    @Transactional
    public OrderResponseDto updateOrder(UUID orderId, OrderRequestDto requestDto){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Not found id" + orderId));

        if(order.getStatus() != OrderStatus.CREATED){
            throw new RuntimeException("CREATED Status에서만 Update 가능");
        }
        order.updateOrderDetails(
                requestDto.getName(),
                requestDto.getSupplierId(),
                requestDto.getReceiverId(),
                requestDto.getProductId(),
                requestDto.getTotalPrice(),
                requestDto.getRequestDetail()
        );
        return new OrderResponseDto(order);

    }



}
