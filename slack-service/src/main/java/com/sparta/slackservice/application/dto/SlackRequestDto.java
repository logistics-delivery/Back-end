package com.sparta.slackservice.application.dto;

import com.sparta.slackservice.domain.model.Slack;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SlackRequestDto {
    private String message;
    private Long receiverId;


    public Slack createSlack(String slack_name,Long userId) {
        return Slack.builder()
                .slackName(slack_name)
                .message(message)
                .receiverId(receiverId)
                .build();
    }
}
