package com.sparta.slackservice.domain.repository;

import com.sparta.slackservice.application.dto.SlackResponseDto;
import com.sparta.slackservice.application.dto.SlackSearchRequestDto;
import com.sparta.slackservice.domain.model.Slack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SlackRepository {
    Page<SlackResponseDto> searchSlack(SlackSearchRequestDto requestDto, Pageable pageable);

}
