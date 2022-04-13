package com.sparta.bucket.dto;

import com.sparta.bucket.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {
        private Long id;

        private String content;
        private Boolean done;
        public TodoResponseDto(Todo todo) {
                this.id = todo.getId();
                this.content = todo.getContent();
                this.done = todo.getDone();
        }
}

