package com.sparta.user.application.dto.response;

import com.sparta.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponseDto {
    private String username;
    private String email;
    private String slackName;
    private String role;

    public UserInfoResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.slackName = user.getSlackName();
        this.role = user.getRole();
    }
}
