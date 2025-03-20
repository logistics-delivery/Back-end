package com.sparta.user.presentation;


import com.sparta.user.application.dto.request.UserSigninReqeustDto;
import com.sparta.user.application.dto.request.UserSignupRequestDto;
import com.sparta.user.application.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.AuthenticationException;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
    public ResponseEntity<Void> signIn(@Valid @RequestBody UserSigninReqeustDto reqeustDto,
    HttpServletResponse httpServletResponse) throws AuthenticationException {
        String accessToken= userService.signIn(reqeustDto);
        //HttpHeaders(중요)
        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        return ResponseEntity.ok().build();
    }
}