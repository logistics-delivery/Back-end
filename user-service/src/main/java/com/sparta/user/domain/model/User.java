package com.sparta.user.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "p_users")
@Entity
public class User{

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

    @Column(name = "slack_name", length = 30, nullable = false)
    private String slackName;

    @Column(nullable = false)
    private String role;



    public User(Long id, String username, String password, String email, String slackName, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.slackName = slackName;
        this.role = role;
    }
}
