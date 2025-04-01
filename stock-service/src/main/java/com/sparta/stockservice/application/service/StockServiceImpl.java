package com.sparta.stockservice.application.service;

import com.sparta.commonmodule.exception.ResourceNotFoundException;
import com.sparta.stockservice.application.dto.DecreaseStockServiceRequestDto;
import com.sparta.stockservice.application.dto.IncreaseStockServiceRequestDto;
import com.sparta.stockservice.domain.model.Stock;
import com.sparta.stockservice.domain.repository.StockRepository;
import com.sparta.stockservice.presentation.dto.request.CreateStockRequestDto;
import com.sparta.stockservice.presentation.dto.response.CreateStockResponseDto;
import com.sparta.stockservice.presentation.dto.response.DecreaseStockResponseDto;
import com.sparta.stockservice.presentation.dto.response.IncreaseStockResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;


    /**
     *  재고 감소
     */
    @Override
    public DecreaseStockResponseDto decreaseStock(DecreaseStockServiceRequestDto serviceDto) {
        Stock stock = stockRepository.findByProductIdAndHubId(serviceDto.productId(), serviceDto.hubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 허브에 상품이 존재하지 않습니다."));

        try {
            // 재고 감소 성공
            stock.decreaseStock(serviceDto.quantity());
            return DecreaseStockResponseDto.success(serviceDto.productId(), serviceDto.quantity());
        } catch (IllegalArgumentException | IllegalStateException e) {
            // 재고 감소 실패 (재고 부족, 최소 수량 미만 등)
            return DecreaseStockResponseDto.failure(serviceDto.productId());
        }
    }


    /**
     *  재고 증가
     */
    @Override
    public IncreaseStockResponseDto increaseStock(IncreaseStockServiceRequestDto serviceDto) {
        Stock stock  = stockRepository.findByProductIdAndHubId(serviceDto.productId(), serviceDto.hubId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 허브에 상품이 존재하지 않습니다."));

        stock.increaseStock(serviceDto.quantity());

        return IncreaseStockResponseDto.success(serviceDto.productId(), serviceDto.quantity());
    }

}
