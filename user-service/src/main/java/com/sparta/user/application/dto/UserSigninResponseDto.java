package com.sparta.user.application.dto;

import com.sparta.user.domain.model.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSigninResponseDto {
    private Long id;
    private UserRoleEnum role;
    private String slackName;
}
