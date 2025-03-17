package com.sparta.orderservice.application.service;

import com.sparta.orderservice.application.dto.OrderRequestDto;
import com.sparta.orderservice.domain.model.Order;
import com.sparta.orderservice.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
