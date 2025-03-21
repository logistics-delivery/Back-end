package com.sparta.user.application.dto.response;

import com.sparta.user.domain.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserSigninResponseDto {
    private Long id;
    private String role;
    private String slackName;

    public UserSigninResponseDto(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.slackName = user.getSlackName();
    }
}
