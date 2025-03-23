package com.sparta.slackservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SlackSearchRequestDto {
    private String message;
    private Long receiverId;
    private String slackName;
}
