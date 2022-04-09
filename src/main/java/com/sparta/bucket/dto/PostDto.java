package com.sparta.bucket.dto;

import com.sparta.bucket.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private String title;
    private List<Todo> todo;
}
