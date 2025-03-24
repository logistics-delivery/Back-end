package com.sparta.slackservice.presentation;

import com.sparta.slackservice.application.service.GeminiService;
import com.sparta.slackservice.infastructure.client.OrderClient;
import com.sparta.slackservice.infastructure.client.dto.GeminiResponseDto;
import com.sparta.slackservice.infastructure.client.dto.SlackNotificationDto;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class GeminiController {

    private GeminiService geminiService;
    private OrderClient orderClient;

    public GeminiController(GeminiService geminiService, OrderClient orderClient) {
        this.geminiService = geminiService;
        this.orderClient = orderClient;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<GeminiResponseDto> createMessage(@PathVariable UUID id) {//주문정보 입력
        //요청한 정보가져오기
        SlackNotificationDto slackNotificationDto = orderClient.getSlackNotificationInfo(id);

        GeminiResponseDto responseDto = geminiService.generateResponse(slackNotificationDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/create")
    public ResponseEntity<GeminiResponseDto> createMessage(@RequestBody SlackNotificationDto requestDto) {//주문정보 입력
        //요청한 정보가져오기
        GeminiResponseDto responseDto = geminiService.generateResponse(requestDto);
        return ResponseEntity.ok(responseDto);
    }


}