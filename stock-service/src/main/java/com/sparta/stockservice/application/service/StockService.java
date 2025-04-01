package com.sparta.stockservice.application.service;


import com.sparta.stockservice.application.dto.DecreaseStockServiceRequestDto;
import com.sparta.stockservice.application.dto.IncreaseStockServiceRequestDto;
import com.sparta.stockservice.presentation.dto.request.CreateStockRequestDto;
import com.sparta.stockservice.presentation.dto.response.CreateStockResponseDto;
import com.sparta.stockservice.presentation.dto.response.DecreaseStockResponseDto;
import com.sparta.stockservice.presentation.dto.response.IncreaseStockResponseDto;

public interface StockService {

    CreateStockResponseDto createStock(CreateStockRequestDto requestDto);

    DecreaseStockResponseDto decreaseStock(DecreaseStockServiceRequestDto serviceDto);

    IncreaseStockResponseDto increaseStock(IncreaseStockServiceRequestDto serviceDto);
}
