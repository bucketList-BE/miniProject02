package com.sparta.bucket.service;

import com.sparta.bucket.dto.TodoDeleteResponseDto;
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
    public TodoDeleteResponseDto deleteTodoList(Long postId, Long todoNum) {
        TodoDeleteResponseDto todoDeleteResponseDto = new TodoDeleteResponseDto();
        // 어차피 밑에서 exception 잡으니까 초기화 해줄 필요가 없을 듯....
//        todoDeleteResponseDto.setDeleteTodoResult(false);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시글은 존재하지 않습니다.")
        );

        List<Todo> todoList = post.getTodo();

        for(Todo todo : todoList){
            if (Objects.equals(todo.getId(), todoNum)) {
                todoRepository.deleteById(todoNum);
                todoDeleteResponseDto.setDeleteTodoResult(true);
            }
        }
        return todoDeleteResponseDto;
    }
}
