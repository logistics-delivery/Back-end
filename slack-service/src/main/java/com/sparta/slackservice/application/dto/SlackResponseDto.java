package com.sparta.slackservice.application.dto;

import com.sparta.slackservice.domain.model.Slack;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class SlackResponseDto {
    private UUID slackId;
    private String slackName;
    private String message;
    private LocalDateTime sendedAt;
    private boolean sendingStatus;


    public SlackResponseDto(Slack slack) {
        this.slackId = slack.getId();
        this.slackName = slack.getSlackName();
        this.message = slack.getMessage();
        this.sendedAt = slack.getSendedAt();
        this.sendingStatus = slack.isSendingStatus();
    }
}
