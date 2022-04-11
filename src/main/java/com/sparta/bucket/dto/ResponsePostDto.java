package com.sparta.bucket.dto;

import com.sparta.bucket.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostDto {
    private String title;
    private String imageUrl;
    private LocalDateTime createdAt;
    private List<Todo> todo;


}