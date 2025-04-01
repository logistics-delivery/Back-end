package com.sparta.stockservice.presentation;

import com.sparta.stockservice.presentation.dto.request.CreateStockRequestDto;
import com.sparta.stockservice.presentation.dto.response.CreateStockResponseDto;
import com.sparta.stockservice.presentation.dto.response.DecreaseStockResponseDto;
import com.sparta.stockservice.presentation.dto.response.IncreaseStockResponseDto;
import com.sparta.stockservice.application.dto.DecreaseStockServiceRequestDto;
import com.sparta.stockservice.application.dto.IncreaseStockServiceRequestDto;
import com.sparta.stockservice.application.service.StockService;
import com.sparta.stockservice.presentation.dto.request.DecreaseStockRequestDto;
import com.sparta.stockservice.presentation.dto.request.IncreaseStockRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    /**
     *  재고 생성
     */
    @PostMapping
    public ResponseEntity<CreateStockResponseDto> createStock(@RequestBody CreateStockRequestDto requestDto,
                                                              @RequestHeader(value = "user_id", required = true) Long user_id){
        return ResponseEntity.ok(stockService.createStock(requestDto));
    }


    /**
     *  재고 감소
     */
    @PutMapping("/{productId}/decrease")
    public ResponseEntity<DecreaseStockResponseDto> decreaseStock(@PathVariable UUID productId,
                                                                  @RequestBody DecreaseStockRequestDto requestDto) {
        return ResponseEntity.ok(stockService.decreaseStock(
                DecreaseStockServiceRequestDto.of(requestDto, productId)));
    }


    /**
     *  재고 증가
     */
    @PutMapping("/{productId}/increase")
    public ResponseEntity<IncreaseStockResponseDto> increaseStock(@PathVariable UUID productId,
                                                                  @RequestBody IncreaseStockRequestDto requestDto) {
        return ResponseEntity.ok(stockService.increaseStock(
                IncreaseStockServiceRequestDto.of(requestDto, productId)));
    }

}
