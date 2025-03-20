package com.sparta.user.application.service;

import com.sparta.user.application.dto.request.UserSigninReqeustDto;
import com.sparta.user.application.dto.request.UserUpdateRequestDto;
import com.sparta.user.application.dto.response.UserSigninResponseDto;
import com.sparta.user.application.dto.request.UserSignupRequestDto;
import com.sparta.user.domain.model.User;
import com.sparta.user.domain.model.UserRoleEnum;
import com.sparta.user.infastructure.configuration.AuthConfig;
import com.sparta.user.infastructure.repository.JpaUserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthConfig authConfig;
    private final AuthService authService;

    //회원가입
    public Long signUp(UserSignupRequestDto requestDto) throws IllegalAccessException {
        validDuplicatedNames(requestDto);
        UserRoleEnum role = checkUserRole(requestDto.getTokenValue());
        User user = requestDto.createUser(encryptPassword(requestDto.getPassword()), role);
        return userRepository.save(user).getId();
    }
    //로그인
    public String signIn(UserSigninReqeustDto reqeustDto) throws AuthenticationException {
        User user = userRepository.findByUsername(reqeustDto.getUsername())
                .orElseThrow(()-> new AuthenticationException("아이디가 존재하지않습니다."));
        passwordMatchChecker(reqeustDto,user);
        UserSigninResponseDto responseDto = new UserSigninResponseDto(user);
        return authService.createAccessToken(responseDto);
    }
    //회원정보 수정
    public void updateUser(UserUpdateRequestDto requestDto, String userId) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(()->new IllegalArgumentException("존재하지않는 회원입니다."));
        UserRoleEnum userRole = checkUserRole(requestDto.getTokenValue());
        user.updateUser(encryptPassword(requestDto.getPassword()), requestDto, userRole.getAuthority());

        userRepository.save(user);
    }

    //비밀번호 인증
    private void passwordMatchChecker(UserSigninReqeustDto reqeustDto,  User user) throws AuthenticationException {
        boolean pwcheck = passwordEncoder.matches(reqeustDto.getPassword(), user.getPassword());
        if(!pwcheck){
            throw new AuthenticationException("아이디나 비밀번호가 일치하지않습니다.");// 일치하지않는 부분 특정방지
        }
    }
    //권한 키 체크
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
    //비밀번호 암호화
    private String encryptPassword (String password) {
        if(password == null){
            throw new NullArgumentException("비밀번호를 입력해주세요");
        }
        return passwordEncoder.encode(password);
    }

    //중복이름방지
    private void validDuplicatedNames(UserSignupRequestDto requestDto) throws IllegalAccessException {
        boolean exsist = userRepository.existsByUsername(requestDto.getUsername());
        if(exsist){
            throw new IllegalAccessException("Username or Email or SlackName is already taken.");
        }
    }



}
