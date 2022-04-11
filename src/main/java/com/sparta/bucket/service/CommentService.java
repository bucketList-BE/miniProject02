package com.sparta.bucket.service;

import com.sparta.bucket.dto.CommentRequestDto;
import com.sparta.bucket.dto.CommentResponseDto;
import com.sparta.bucket.dto.ResultResponseDto;
import com.sparta.bucket.model.Comment;
import com.sparta.bucket.model.Post;
import com.sparta.bucket.repository.CommentRepository;
import com.sparta.bucket.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    public CommentResponseDto postComment(Long postId, CommentRequestDto commentRequestDto) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));
        Comment comment = new Comment(savedPost, commentRequestDto);
        comment = commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public CommentResponseDto putComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("해당 댓글이 없습니다."));
        comment.setComment(commentRequestDto.getComment());
        comment.setEditCheck(true);
        comment = commentRepository.save(comment);

        return new CommentResponseDto(comment);

    }

    public ResultResponseDto deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return new ResultResponseDto(true);
    }
}
