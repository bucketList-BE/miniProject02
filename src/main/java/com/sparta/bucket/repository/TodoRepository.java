package com.sparta.bucket.repository;

import com.sparta.bucket.model.Post;
import com.sparta.bucket.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllById(Long postId);
}
