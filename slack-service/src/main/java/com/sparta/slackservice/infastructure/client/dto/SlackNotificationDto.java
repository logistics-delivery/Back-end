package com.sparta.slackservice.infastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Slack 알림 관련해서 응답 DTO
public class SlackNotificationDto {

    private UUID orderId;
    private UUID shippingId;
    private String shippingStatus;
    private String route;
    private String hubName;
    private String hubManagerName;
    private String message;
}
