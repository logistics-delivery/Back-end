package com.sparta.shippingmanager.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.shippingmanager.application.dto.request.ShippingManagerCreateRequestDto;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingmanager.domain.repository.ShippingManagerRepository;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ShippingManagerService {

    private final ShippingManagerRepository repository;

    private static final int MAX_ORDER = 10;
    private static int MIN_ORDER =0;

    @Transactional
    public ShippingManager assign() {
        ShippingManager manager = repository.findNextManagerWithLock()
                .stream().
                findFirst().
                orElseThrow(() -> new ResourceNotFoundException(" 배정 가능한 담당자가 없습니다."));

        //manager.increaseCount(MIN_ORDER);

        nextManagerOrder(manager.getShippingOrder());

        return manager;

    }
    @Transactional
    public ShippingManagerResponseDto create(ShippingManagerCreateRequestDto request,Long userId){
        ShippingManager shippingManager = request.of().toShippingManager(userId);
        repository.save(shippingManager);
        return ShippingManagerResponseDto.from(shippingManager);

    }
    @Transactional(readOnly = true)
    public ShippingManagerResponseDto getById(UUID managerId){
        ShippingManager manager = repository.findById(managerId).orElseThrow(() -> new ResourceNotFoundException("해당 ID의 배송자는 존재하지 않습니다."));
        return ShippingManagerResponseDto.from(manager);
    }


    private void nextManagerOrder(int currentOrder) {
        int nextOrder;
        if (currentOrder + 1 > MAX_ORDER) {
            nextOrder = 1;
        } else {
            nextOrder = currentOrder + 1;
        }
        repository.updateAllManagerOrders(nextOrder);

    }

}
