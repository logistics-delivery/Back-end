package com.sparta.slackservice.application.service;


import com.slack.api.Slack;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import com.sparta.slackservice.infastructure.JpaSlackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SlackWebhookService {//슬랙 api 메세지 전송 서비스
    @Value("${slack.webhook.url}")
    private String slackWebhookUrl; //알림을 전송할 url

    private final Slack slack = Slack.getInstance();

    private final JpaSlackRepository slackRepository;

    public WebhookResponse sendMessage(String message) {
        Payload payload = Payload.builder().text(message).build();
        // WebhookResponse(code=200, message=OK, body=ok)
        WebhookResponse response;
        try {
            response = slack.send(slackWebhookUrl, payload);
            System.out.println(response);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
