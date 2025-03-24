package com.sparta.slackservice.application.service;

import com.sparta.slackservice.application.dto.SlackResponseDto;
import com.sparta.slackservice.infastructure.client.dto.GeminiReqDto;
import com.sparta.slackservice.infastructure.client.dto.GeminiResDto;
import com.sparta.slackservice.infastructure.client.dto.GeminiResponseDto;
import com.sparta.slackservice.infastructure.client.dto.SlackNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final RestTemplate restTemplate;

    public GeminiResponseDto generateResponse(SlackNotificationDto requestDto) {
        String geminiURL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
                + "AIzaSyDrLB8TPWb0uhedaw9Oy4dxMl2Rm5EU-U4";
        String context = createCombinedMessage(requestDto);
        String requestText = context + "에 대해서 하나의 배송메세지를 작성해줘";
        GeminiReqDto request = new GeminiReqDto();
        request.createGeminiReqDto(requestText);

        GeminiResponseDto responseDto = new GeminiResponseDto();
        try{
            String response = restTemplate.postForObject(geminiURL, request, String.class);
            responseDto.setAnswer(response);
        }catch (Exception e){
            throw new RuntimeException();
        }

        return responseDto;
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
