package com.sparta.slackservice.application.service;

import com.sparta.slackservice.application.dto.SlackRequestDto;
import com.sparta.slackservice.application.dto.SlackResponseDto;
import com.sparta.slackservice.domain.model.Slack;
import com.sparta.slackservice.infastructure.JpaSlackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SlackService {

    private final JpaSlackRepository slackRepository;
    private final SlackWebhookService slackWebhookService;

    //슬랙 메세지 생성
    public SlackResponseDto createSlack(String slack_name, SlackRequestDto requestDto) {
        Slack slack = slackRepository.save(requestDto.createSlack(slack_name));
        return new SlackResponseDto(slack);
    }

    //메세지 전송
    public void sendSlack(UUID slackId) {
        Slack slack = findingSlack(slackId);
        slack.changeStatus();
        slackWebhookService.sendMessage(slack.getMessage());
    }

    //메세지 조회(단건)
    @Transactional(readOnly = true)
    public Slack getSlack(UUID slackId) {
        return findingSlack(slackId);
    }

    //메세지 수정
    public SlackResponseDto modifySlack(UUID slackId, SlackRequestDto requestDto) {
        Slack slack = findingSlack(slackId);
        slack.modifySlack(requestDto);
        return new SlackResponseDto(slack);
    }

    //메세지 삭제
    public void deleteSlack(UUID slackId, Long userId) {
        Slack slack = findingSlack(slackId);
        slack.delete(userId);
    }

    private Slack findingSlack(UUID slackId) {
        return slackRepository.findById(slackId).orElseThrow(() -> new RuntimeException("Slack not found"));
    }


}
