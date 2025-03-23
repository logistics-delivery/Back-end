package com.sparta.slackservice.presentation;

import com.sparta.slackservice.application.dto.SlackRequestDto;
import com.sparta.slackservice.application.dto.SlackResponseDto;
import com.sparta.slackservice.application.dto.SlackSearchRequestDto;
import com.sparta.slackservice.application.service.SlackService;
import com.sparta.slackservice.application.service.SlackWebhookService;
import com.sparta.slackservice.domain.model.Slack;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/slacks")
@RequiredArgsConstructor
@Tag(name = "Slack Service", description = "슬랙 메세지 서비스 API")
public class SlackController {

    private final SlackService slackService;
    private final SlackWebhookService slackWebhookService;

    @Operation(summary = "메세지 생성", description = "메세지 생성 api입니다.")
    @PostMapping("/create")
    public ResponseEntity<SlackResponseDto> createSlack(@RequestHeader("user_id") Long userId, @RequestHeader("slack_name") String slack_name, @RequestBody SlackRequestDto requestDto) {
        return ResponseEntity.ok(slackService.createSlack(slack_name, requestDto, userId));
    }

    @Operation(summary = "메세지 전송", description = "메세지 전송 api입니다.")
    @GetMapping("/send/{id}")
    public ResponseEntity<Object> sendSlack(@PathVariable("id") UUID slackId) {
        slackService.sendSlack(slackId);
        return ResponseEntity.ok("전송성공");
    }

    @Operation(summary = "메세지 조회(단건)", description = "메세지 조회(단건) api입니다.")
    @GetMapping("{id}")
    public ResponseEntity<Slack> getSlack(@PathVariable("id") UUID slackId) {
        return ResponseEntity.ok(slackService.getSlack(slackId));
    }

    @Operation(summary = "메세지 조회(전체)", description = "메세지 조회(전체) api입니다.")
    @PostMapping("/search")
    public ResponseEntity<Page<SlackResponseDto>> searchSlack(
            @RequestBody SlackSearchRequestDto requestDto,
            @PageableDefault(page = 0, size = 10, sort = "createdAt") Pageable pageable) {
        Page<SlackResponseDto> responseDto= slackService.searchSlack(requestDto,pageable);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "메세지 수정", description = "메세지 수정 api입니다.")
    @PutMapping("/modify/{id}")
    public ResponseEntity<?> modifySlack(@RequestHeader("user_id") Long userId,@PathVariable("id") UUID slackId, @RequestBody SlackRequestDto requestDto) {
        return ResponseEntity.ok(slackService.modifySlack(slackId, requestDto, userId));
    }

    @Operation(summary = "메세지 삭제", description = "메세지 삭제 api입니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlack(@PathVariable("id") UUID slackId, @RequestHeader("user_id") Long userId) {
        slackService.deleteSlack(slackId, userId);
        return ResponseEntity.noContent().build();//삭제 id, 메세지 출력
    }

}
