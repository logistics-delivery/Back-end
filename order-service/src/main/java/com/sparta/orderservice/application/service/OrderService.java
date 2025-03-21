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


    //주문 생성
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = Order.builder()
                .name(requestDto.getName())
                .supplierId(requestDto.getSupplierId())
                .receiverId(requestDto.getReceiverId())
                .productId(requestDto.getProductId())
                .totalPrice(requestDto.getTotalPrice())
                .requestDetail(requestDto.getRequestDetail())
                .status(OrderStatus.CREATED)
                .build();

        order.setCreatedBy(0L);  // createdBy 기본값 설정 (BaseEntity 상속으로 인해 필요)

        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    // 주문 전체 조회
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getALlOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
    // 주문 단일 조회
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(UUID orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order Not found : " + orderId));
        return OrderResponseDto.fromEntity(order);
    }

    // 주문 수정
    @Transactional
    public OrderResponseDto updateOrder(UUID orderId, OrderRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Not found id" + orderId));

        if (order.getStatus() != OrderStatus.CREATED) {
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

    // 주문 삭제
    @Transactional
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Not found id" + orderId));

        order.softDelete();
    }


}
