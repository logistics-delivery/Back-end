package com.sparta.user.domain.model;

import com.sparta.commonmodule.entity.BaseEntity;
import com.sparta.user.application.dto.request.UserSignupRequestDto;
import com.sparta.user.application.dto.request.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
@Table(name = "p_users")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(length = 10, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(name = "slack_name", length = 30)
    private String slackName;

    @Column(nullable = false)
    private String role;


    @Builder
    public User(String username, String password, String email, String slackName, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.slackName = slackName;
        this.role = role;
    }

    @Builder
    public User(String username, String password, String email, String slackName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.slackName = slackName;
    }


    public void updateUser(String encryptedpassword, UserUpdateRequestDto requestDto, String userRole) {
        Optional.ofNullable(requestDto.getUsername()).ifPresent(username -> this.username = username);
        Optional.ofNullable(encryptedpassword).ifPresent(password -> this.password = encryptedpassword);
        Optional.ofNullable(requestDto.getEmail()).ifPresent(email -> this.email = email);
        Optional.ofNullable(requestDto.getSlackName()).ifPresent(slackName -> this.slackName = slackName);
        Optional.ofNullable(userRole).ifPresent(role -> this.role = userRole);
    }
}
