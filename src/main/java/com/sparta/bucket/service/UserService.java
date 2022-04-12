package com.sparta.bucket.service;

import com.sparta.bucket.dto.ResultResponseDto;
import com.sparta.bucket.model.User;
import com.sparta.bucket.dto.SignupRequestDto;
import com.sparta.bucket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();

        // 패스워드
        String enPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(username,nickname,enPassword);
        userRepository.save(user); // DB 저장

    }

    // 아이디 중복확인
    public ResultResponseDto duplicateUsername(String username) {
        return new ResultResponseDto(userRepository.existsByUsername(username));
    }
    // 닉네임 중복확인
    public ResultResponseDto duplicatecNickname(String nickname) {
        return new ResultResponseDto(userRepository.existsByNickname(nickname));
    }
}
