package com.sparta.bucket.controller;


import com.sparta.bucket.dto.ResultResponseDto;
import com.sparta.bucket.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post/{postId}/todo/{todoNum}")
public class TodoController {

    private final TodoService todoService;

    //TodoList 삭제
    @DeleteMapping("")
    public ResultResponseDto deleteTodoList(
            @PathVariable Long postId,
            @PathVariable Long todoNum
    ) {
        return todoService.deleteTodoList(postId, todoNum);
    }

    @PutMapping("")
    public ResultResponseDto toggleTodo(@PathVariable Long todoNum , @RequestParam Integer done) {
        if (done == null) {
            throw new IllegalArgumentException("done 값의 입력이 필요합니다.");
        }
        return todoService.toggleTodo(todoNum,done);
    }
}
