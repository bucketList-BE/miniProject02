package com.sparta.bucket.dto;

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
public class PostResponseDto {
    private String title;
    private String imageUrl;
    private LocalDateTime createdAt;
    private List<TodoResponseDto> todo;


}