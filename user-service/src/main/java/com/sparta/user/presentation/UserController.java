package com.sparta.user.presentation;


import com.sparta.user.application.dto.request.UserSigninReqeustDto;
import com.sparta.user.application.dto.request.UserSignupRequestDto;
import com.sparta.user.application.dto.request.UserUpdateRequestDto;
import com.sparta.user.application.dto.response.UserInfoResponseDto;
import com.sparta.user.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.AuthenticationException;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Service", description = "사용자 서비스 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "화원가입 api입니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserSignupRequestDto requestDto, BindingResult bindingResult) throws IllegalAccessException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Long userId  = userService.signUp(requestDto);
        URI createdUserUri = UriComponentsBuilder
                .fromUriString("/api/v1/users/{id}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(createdUserUri).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Void> signIn(@Valid @RequestBody UserSigninReqeustDto requestDto,
    HttpServletResponse httpServletResponse) throws AuthenticationException {
        String accessToken= userService.signIn(requestDto);
        //HttpHeaders(중요)
        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@RequestHeader("user_id") Long userId) {
        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfoResponseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdateRequestDto requestDto, @RequestHeader("user_id") Long userId) {
        userService.updateUser(requestDto, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestHeader("user_id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}