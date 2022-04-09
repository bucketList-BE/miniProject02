package com.sparta.bucket.dto;

import com.sparta.bucket.model.Todo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResponsePostDto {
    private String title;
    private LocalDateTime createdAt;
    private List<Todo> todo;
}