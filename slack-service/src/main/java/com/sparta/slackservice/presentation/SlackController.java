package com.sparta.slackservice.presentation;

import com.sparta.slackservice.application.dto.SlackRequestDto;
import com.sparta.slackservice.application.dto.SlackResponseDto;
import com.sparta.slackservice.application.service.SlackService;
import com.sparta.slackservice.application.service.SlackWebhookService;
import com.sparta.slackservice.domain.model.Slack;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/slacks")
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;
    private final SlackWebhookService slackWebhookService;

    @PostMapping("/create")
    public ResponseEntity<SlackResponseDto> createSlack(@RequestHeader("slack_name") String slack_name, @RequestBody SlackRequestDto requestDto) {
        return ResponseEntity.ok(slackService.createSlack(slack_name, requestDto));
    }

    @GetMapping("/send/{id}")
    public ResponseEntity<Object> sendSlack(@PathVariable("id") UUID slackId) {
        slackService.sendSlack(slackId);
        return ResponseEntity.ok("전송성공");
    }

    @GetMapping("{id}")
    public ResponseEntity<Slack> getSlack(@PathVariable("id") UUID slackId) {
        return ResponseEntity.ok(slackService.getSlack(slackId));
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> modifySlack(@PathVariable("id") UUID slackId, @RequestBody SlackRequestDto requestDto) {
        slackService.modifySlack(slackId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlack(@PathVariable("id") UUID slackId, @RequestHeader("user_id") Long userId) {
        slackService.deleteSlack(slackId, userId);
        return ResponseEntity.noContent().build();//삭제 id, 메세지 출력
    }

}
