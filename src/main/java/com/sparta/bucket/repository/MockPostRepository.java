package com.sparta.bucket.repository;

import com.sparta.bucket.model.MockPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MockPostRepository extends JpaRepository<MockPost,Long> {
}
