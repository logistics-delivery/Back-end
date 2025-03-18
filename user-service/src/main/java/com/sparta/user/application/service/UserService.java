package com.sparta.user.application.service;

import com.sparta.user.application.dto.UserSigninReqeustDto;
import com.sparta.user.application.dto.UserSigninResponseDto;
import com.sparta.user.application.dto.UserSignupRequestDto;
import com.sparta.user.domain.model.User;
import com.sparta.user.domain.model.UserRoleEnum;
import com.sparta.user.infastructure.configuration.AuthConfig;
import com.sparta.user.infastructure.repository.JpaUserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthConfig authConfig;

    public ResponseEntity<?> signUp(UserSignupRequestDto requestDto) {
        //중복 확인
        Optional<User> duplicate = userRepository.findDulicate(requestDto.getUsername(),
                requestDto.getEmail(), requestDto.getSlackName());
        if(duplicate.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Email is already taken.");
        }
        //권한 설정
        UserRoleEnum role = checkUserRole(requestDto.getTokenValue());

        User user = userRepository.save(
                User.builder()
                        .username(requestDto.getUsername())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .email(requestDto.getEmail())
                        .slackName(requestDto.getSlackName())
                        .role(role.getAuthority())
                        .build()
        );
        return ResponseEntity.ok("sign-up success");
    }

    public UserSigninResponseDto signIn(@Valid UserSigninReqeustDto reqeustDto) throws AuthenticationException {
        //아이디 비밀번호 일치여부 확인.
        Optional<User> userinfo = userRepository.findByUsername(reqeustDto.getUsername());

        User user = userinfo.orElseThrow(()-> new AuthenticationException("아이디가 존재하지않습니다."));
        boolean pwcheck =  passwordEncoder.matches(reqeustDto.getPassword(), user.getPassword()); //비밀번호 일치여부 판단

        if(!pwcheck){
            throw new AuthenticationException("아이디나 비밀번호가 일치하지않습니다.");// 일치하지않는 부분 특정방지
        }

        //비밀번호 일치 (토큰 생성을 위한 dto 전달.)
        UserSigninResponseDto responseDto = new UserSigninResponseDto();
        responseDto.setId(user.getId());
        responseDto.setRole(user.getRole());
        responseDto.setSlackName(user.getSlackName());

        return responseDto;
    }

    private UserRoleEnum checkUserRole(String tokenValue) {
        if (authConfig.getMasterKey().equals(tokenValue)) {
            return UserRoleEnum.MASTER;
        } else if (authConfig.getHubKey().equals(tokenValue)) {
            return UserRoleEnum.HUB;
        } else if (authConfig.getShippingKey().equals(tokenValue)) {
            return UserRoleEnum.SHIPPING;
        } else {
            return UserRoleEnum.COMPANY;
        }
    }


}
