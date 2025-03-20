package com.sparta.user.application.dto.request;

import com.sparta.user.domain.model.User;
import com.sparta.user.domain.model.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class UserUpdateRequestDto {

    //최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9^\\s]{4,10}$",
            message = "회원이름은 알파벳 소문자와 숫자로 이루어진 4자 이상 10자 이하로 입력해주세요.")
    private String username;

    //최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d\\s])[^\\s]{8,15}$",
            message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 포함한 8자 이상 15자 이하입니다.")
    private String password;

    @Email(message = "유효한 이메일 주소를 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,25}$", message = "유효한 이메일 주소를 입력하세요.")
    private String email;

    private String slackName;

    private String tokenValue;


}

