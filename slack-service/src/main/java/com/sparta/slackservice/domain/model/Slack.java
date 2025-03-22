package com.sparta.slackservice.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import com.sparta.slackservice.application.dto.SlackRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Slack extends BaseEntity {

    @Id
    @Column(name = "slack_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "slack_name", nullable = false)
    private String slackName;

    @Column(nullable = false)
    private String message;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "sended_at")
    private LocalDateTime sendedAt;

    @Column(name = "sending_status")
    private boolean sendingStatus = false; //기본 true? false?

    @Builder
    public Slack(String slackName, String message, Long receiverId) {
        this.slackName = slackName;
        this.message = message;
        this.receiverId = receiverId;
    }

    public void changeStatus() {
        this.sendingStatus = true;
        this.sendedAt = LocalDateTime.now();
    }

    public void modifySlack(SlackRequestDto requestDto) {
        Optional.ofNullable(requestDto.getMessage()).ifPresent(message -> this.message = message);
        Optional.ofNullable(requestDto.getReceiverId()).ifPresent(receiverId -> this.receiverId = receiverId);
    }

}
