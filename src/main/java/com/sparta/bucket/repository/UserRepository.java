package com.sparta.bucket.repository;

import com.sparta.bucket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
