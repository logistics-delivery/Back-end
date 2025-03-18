package com.sparta.user.presentation;


import com.sparta.user.application.dto.UserSigninReqeustDto;
import com.sparta.user.application.dto.UserSigninResponseDto;
import com.sparta.user.application.dto.UserSignupRequestDto;
import com.sparta.user.application.service.AuthService;
import com.sparta.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignupRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { //회원가입 요구사항 검증
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage()); //에러코드
        }
        return userService.signUp(requestDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSigninReqeustDto reqeustDto) throws AuthenticationException {

        //토큰 생성을 위한 user 정보 추출
        UserSigninResponseDto responseDto
                = userService.signIn(reqeustDto);

//        //토큰생성은 auth Service에서 진행(추출한 user 정보로 토큰생성 후 엑세스 토큰값 전달.)
//        return ResponseEntity.ok(new AuthResponse(authService.createAccessToken(responseDto)));

        // 토큰 생성하여 추출
        String accessToken = authService.createAccessToken(responseDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken); //ResponseEntity에 추가하기위한 HTTP헤더 생성

        //토큰생성은 auth Service에서 진행(추출한 user 정보로 토큰생성 후 엑세스 토큰값 전달.)

        return ResponseEntity.ok()
                .headers(headers)
                .body("success");

    }

//    /**
//     * JWT 액세스 토큰을 포함하는 응답 객체입니다.
//     */
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    static class AuthResponse {
//        private String access_token;
//
//    }
}