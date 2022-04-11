package com.sparta.bucket.controller;


import com.sparta.bucket.dto.ResultResponseDto;
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

    @PutMapping("/api/post/{postId}/todo/{TodoNum}")
    public ResultResponseDto toggleTodo(@PathVariable Long todoNum , @RequestParam Integer done) {
        if (done == null) {
            throw new IllegalArgumentException("done 값의 입력이 필요합니다.");
        }
        return todoService.toggleTodo(todoNum,done);
    }
}
