package com.sparta.bucket.service;

import com.sparta.bucket.dto.ResultResponseDto;
import com.sparta.bucket.model.Post;
import com.sparta.bucket.model.Todo;
import com.sparta.bucket.repository.PostRepository;
import com.sparta.bucket.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class TodoService {

    private final PostRepository postRepository;
    private final TodoRepository todoRepository;

    //todoList 삭제
    @Transactional
    public ResultResponseDto deleteTodoList(Long postId, Long todoNum) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
        );

        List<Todo> todoList = post.getTodo();

        for(Todo todo : todoList){
            if (Objects.equals(todo.getId(), todoNum)) {
                todoRepository.deleteById(todoNum);
            }
        }
        return new ResultResponseDto(true);
    }

    @Transactional
    public ResultResponseDto toggleTodo(Long todoNum, Integer done) {
        Todo todo = todoRepository.findById(todoNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 하는 Todo 가 없습니다."));

        todo.setDone(done == 1);

        return new ResultResponseDto(true);
    }
}
