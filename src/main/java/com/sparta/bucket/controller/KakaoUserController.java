package com.sparta.bucket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.bucket.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class KakaoUserController {
    private final KakaoUserService kakaoUserService;
    private final String AUTH_HEADER = "Authorization";

//    @ApiOperation("카카오 로그인")
    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        System.out.println("넘어오나요?제발");
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        String token = kakaoUserService.kakaoLogin(code);

        System.out.println("kakao token : " + token);

        response.addHeader(AUTH_HEADER, token);

        return ResponseEntity.ok("fucku");
    }

//    @GetMapping("/oauth/kakao/call")
//    public ResponseEntity<String> call() {
//        return ResponseEntity.ok("call");
//    }

    @GetMapping("/oauth/test")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
}