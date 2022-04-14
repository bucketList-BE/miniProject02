package com.sparta.bucket.repository;

import com.sparta.bucket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 중복확인
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    Optional<User> findByUsername(String username);
}
