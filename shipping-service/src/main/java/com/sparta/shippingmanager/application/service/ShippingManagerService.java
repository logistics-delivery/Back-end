package com.sparta.shippingmanager.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.shippingmanager.application.dto.request.ShippingManagerCreateRequestDto;
import com.sparta.shippingmanager.application.dto.request.ShippingManagerSearchCondition;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerSearchResult;
import com.sparta.shippingmanager.domain.model.ShippingManager;
import com.sparta.shippingmanager.domain.repository.ShippingManagerRepository;
import com.sparta.shippingmanager.application.dto.response.ShippingManagerResponseDto;
import com.sparta.shippingmanager.infrastructure.ShippingManagerSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ShippingManagerService {

    private final ShippingManagerRepository repository;
    private final ShippingManagerSearchRepository searchRepository;

    private static final int MAX_ORDER = 10;


    @Transactional
    public ShippingManager assign() {
        ShippingManager manager = repository.findNextManagerWithLock()
                .stream().
                findFirst().
                orElseThrow(() -> new ResourceNotFoundException(" 배정 가능한 담당자가 없습니다."));


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


    public Page<ShippingManagerResponseDto> search(ShippingManagerSearchCondition condition) {
        ShippingManagerSearchResult result = searchRepository.search(condition);
        return new PageImpl<>(
                result.getContent(),
                PageRequest.of(result.getPage(), result.getPageSize()),
                result.getTotalCount()
        );
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
