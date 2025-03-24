package com.sparta.slackservice.application.service;

import com.sparta.slackservice.infastructure.client.dto.GeminiResponseDto;
import com.sparta.slackservice.infastructure.client.dto.SlackNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final RestTemplate restTemplate;

    public GeminiResponseDto createMessage(SlackNotificationDto requstDto){
        String prompt = createCombinedMessage(requstDto) + "이 데이터를 하나의 배송메세지로 만들어줘";
        String aiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyDrLB8TPWb0uhedaw9Oy4dxMl2Rm5EU-U4";  // 실제 AI 서비스 URL로 수정 필요

        // 요청할 데이터 준비
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("contents", List.of(
                Map.of(
                        "parts", List.of(
                                Map.of("text", prompt)  // text 안에 prompt를 넣음
                        )
                )
        ));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestData, headers);

        // 4. AI에 POST 요청을 보내고 응답 받기
        ResponseEntity<String> aiResponse = restTemplate.postForEntity(aiApiUrl, entity, String.class);

        // 5. AI 응답을 GeminiResponseDto로 변환
        GeminiResponseDto geminiResponse = new GeminiResponseDto();
        geminiResponse.setAnswer(aiResponse.getBody());  // AI 응답 내용 설정

        return geminiResponse;
    }

    private String createCombinedMessage(SlackNotificationDto requestDto) {
        // SlackNotificationDto의 필드를 하나의 메시지로 결합
        return "OrderId: " + requestDto.getOrderId() + "\n" +
                "ShippingId: " + requestDto.getShippingId() + "\n" +
                "ShippingStatus: " + requestDto.getShippingStatus() + "\n" +
                "Route: " + requestDto.getRoute() + "\n" +
                "HubName: " + requestDto.getHubName() + "\n" +
                "HubManagerName: " + requestDto.getHubManagerName() + "\n" +
                "Message: " + requestDto.getMessage();
    }
}
