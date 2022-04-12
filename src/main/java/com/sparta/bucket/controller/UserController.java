package com.sparta.bucket.controller;

import com.sparta.bucket.dto.ResultResponseDto;
import com.sparta.bucket.model.User;
import com.sparta.bucket.dto.SignupRequestDto;
import com.sparta.bucket.dto.UserResponseDto;
import com.sparta.bucket.repository.UserRepository;
import com.sparta.bucket.security.UserDetailsImpl;
import com.sparta.bucket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class
UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/signup")
    public void registerUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
    }

    // 로그인 확인
    @PostMapping("/islogin")
    public UserResponseDto islogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        System.out.println("username : " + user.getUsername());
        System.out.println("nickname : " + user.getNickname());
        return new UserResponseDto(user.getUsername(), user.getNickname());
    }

    // 아이디 중복확인
    @PostMapping("/user/idCheck")
    public ResultResponseDto duplicateUsername(@RequestParam("username") String username) {
        return userService.duplicateUsername(username);
    }

    // 닉네임 중복확인
    @PostMapping("/user/nicknameCheck")
    public ResultResponseDto duplicateNickname(@RequestParam("nickname") String nickname) {
        return userService.duplicatecNickname(nickname);
    }


}



