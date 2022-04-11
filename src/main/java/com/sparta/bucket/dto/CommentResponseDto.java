package com.sparta.bucket.dto;

import com.sparta.bucket.model.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;

    private String comment;

    private String username;

    private LocalDateTime createdAt;

    private boolean editCheck;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getCommentId();
        this.comment = comment.getComment();
        this.username = comment.getUsername();
        this.createdAt = comment.getCreatedAt();
        this.editCheck = comment.isEditCheck();
    }

}
