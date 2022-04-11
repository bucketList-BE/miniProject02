package com.sparta.bucket.service;

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

        //중복된 이메일(로그인 id)가 존재할 경우
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }

        //중복된 닉네임이 존재할 경우
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 패스워드
        String enPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(username,nickname,enPassword);
        userRepository.save(user); // DB 저장

    }
}
