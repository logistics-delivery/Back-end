package com.sparta.orderservice.application.service;

import com.sparta.commonmodule.exception.OperationNotAllowedException;
import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.orderservice.application.dto.OrderRequestDto;
import com.sparta.orderservice.application.dto.OrderResponseDto;
import com.sparta.orderservice.domain.model.Order;
import com.sparta.orderservice.domain.model.OrderStatus;
import com.sparta.orderservice.domain.repository.OrderQueryDSLRepository;
import com.sparta.orderservice.domain.repository.OrderRepository;
import com.sparta.orderservice.infrastructure.client.ProductClient;
import com.sparta.orderservice.infrastructure.client.ShippingClient;
import com.sparta.orderservice.infrastructure.client.dto.request.CreateShippingRequestDto;
import com.sparta.orderservice.infrastructure.client.dto.response.CreateShippingResponseDto;
import com.sparta.orderservice.infrastructure.client.dto.response.DecreaseProductQuantityResponseDto;
import com.sparta.orderservice.infrastructure.client.dto.request.DecreaseProductQuantityRequestDto;
import com.sparta.orderservice.infrastructure.client.dto.response.SlackNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Qualifier("orderQueryDSLRepositoryImpl")
    private final OrderQueryDSLRepository orderQueryDSLRepository;

    private final ProductClient productClient;
    private final ShippingClient shippingClient;

    //주문 생성
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        // 1. 재고 차감 요청 DTO 생성
        DecreaseProductQuantityRequestDto reduceRequest =
                DecreaseProductQuantityRequestDto.builder()
                        .companyId(requestDto.getSupplierId())     // supplierId → companyId
                        .hubId(requestDto.getReceiverId())         // receiverId → hubId
                        .quantity(50)                              // 기본 수량
                        .build();

        // 2. FeignClient로 재고 차감 요청
        DecreaseProductQuantityResponseDto response =
                productClient.decreaseProductQuantity(requestDto.getProductId(), reduceRequest);

        // 3. 실패 시 예외 발생
        if (!response.getIsSuccess()) {
            throw new OperationNotAllowedException("재고 차감에 실패하여 주문을 생성할 수 없습니다.");
        }

        // 4. 주문 저장
        Order order = Order.builder()
                .name(requestDto.getName())
                .supplierId(requestDto.getSupplierId())
                .receiverId(requestDto.getReceiverId())
                .productId(requestDto.getProductId())
                .totalPrice(requestDto.getTotalPrice())
                .requestDetail(requestDto.getRequestDetail())
                .status(OrderStatus.CREATED)
                .build();

        orderRepository.save(order);

        // 5. 배송 요청 DTO 생성
        CreateShippingRequestDto shippingRequest = CreateShippingRequestDto.builder()
                .orderId(order.getOrderId())
                .productId(order.getProductId())
                .supplierId(order.getSupplierId())
                .receiverId(order.getReceiverId())
                .quantity(1)
                .build();

        // 6. FeignClient로 배송 요청
        CreateShippingResponseDto shippingResponse = shippingClient.createShipping(shippingRequest);

        // 7. 배송 실패 시 예외
        if (!"READY".equals(shippingResponse.getStatus())) {
            throw new OperationNotAllowedException("배송 생성 실패로 주문 생성 중단");
        }

        return new OrderResponseDto(order);
    }

    // 주문 전체 조회
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getALlOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 주문 단일 조회
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 주문을 찾을 수 없습니다."));
        return OrderResponseDto.fromEntity(order);
    }

    // 주문 수정
    @Transactional
    public OrderResponseDto updateOrder(UUID orderId, OrderRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 주문을 찾을 수 없습니다."));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new OperationNotAllowedException("CREATED 상태의 주문만 수정할 수 있습니다.");
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
                .orElseThrow(() -> new ResourceNotFoundException("해당 주문을 찾을 수 없습니다."));

        order.softDelete();
    }

    // 주문 취소
    @Transactional
    public OrderResponseDto cancelOrder(UUID orderId, String cancelReason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 주문을 찾을 수 없습니다."));

        order.cancel(cancelReason);
        return OrderResponseDto.fromEntity(order);
    }

    // 주문 검색
    @Transactional(readOnly = true)
    public List<OrderResponseDto> searchOrders(String name, OrderStatus status) {
        List<Order> result = orderQueryDSLRepository.searchOrders(name, status);
        return result.stream()
                .map(OrderResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 슬랙 알림용 DTO 생성 메서드
    @Transactional(readOnly = true)
    public SlackNotificationDto getSlackNotificationDto(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 주문을 찾을 수 없습니다."));

        CreateShippingResponseDto shipping = shippingClient.getShippingInfo(orderId);

        return SlackNotificationDto.builder()
                .orderId(order.getOrderId())
                .shippingId(shipping.getShippingId())
                .shippingStatus(shipping.getStatus())
                .route(shipping.getRoute())
                .hubName(shipping.getHubName())
                .hubManagerName(shipping.getHubManagerName())
                .message(shipping.getMessage())
                .build();
    }
}
