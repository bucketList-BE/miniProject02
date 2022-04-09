package com.sparta.bucket.controller;


import com.sparta.bucket.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
//@ResponseStatus(HttpStatus.OK)
public class TodoController {

    private final TodoService todoService;

    //TodoList 삭제
    @DeleteMapping("/api/post/{postId}/todo/{TodoNum}")
    public Boolean deleteTodoList(
            @PathVariable Long postId,
            @PathVariable Long TodoNum
    ) {
        return todoService.deleteTodoList(postId, TodoNum);
    }
}
