package com.sparta.bucket.controller;

import com.sparta.bucket.dto.CommentRequestDto;
import com.sparta.bucket.dto.CommentResponseDto;
import com.sparta.bucket.dto.ResultResponseDto;
import com.sparta.bucket.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/{postId}")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public CommentResponseDto postComment(@PathVariable(name="postId") Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.postComment(postId, commentRequestDto);
    }
    
    @PutMapping("/{commentId}")
    public CommentResponseDto putComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.putComment(commentId, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public ResultResponseDto deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
