package com.sparta.slackservice.infastructure;

import com.sparta.slackservice.application.dto.SlackRequestDto;
import com.sparta.slackservice.application.dto.SlackResponseDto;
import com.sparta.slackservice.application.dto.SlackSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackQueryDSLRepository {
    Page<SlackResponseDto> searchSlack(SlackSearchRequestDto requestDto, Pageable pageable);
}
