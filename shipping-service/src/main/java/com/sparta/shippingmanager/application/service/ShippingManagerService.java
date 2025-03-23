package com.sparta.shippingmanager.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.shippingmanager.application.dto.request.ShippingManagerCreateRequestDto;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingmanager.domain.repository.ShippingManagerRepository;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

        manager.increaseCount(MIN_ORDER);

        nextManagerOrder(manager.getShippingOrder());

        return manager;

    }

    public ShippingManagerResponseDto create(ShippingManagerCreateRequestDto request,Long userId){
        ShippingManager shippingManager = request.of().toShippingManager(userId);
        repository.save(shippingManager);
        return ShippingManagerResponseDto.from(shippingManager);

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
