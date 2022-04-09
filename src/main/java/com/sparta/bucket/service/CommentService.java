package com.sparta.bucket.service;

import com.sparta.bucket.dto.CommentRequestDto;
import com.sparta.bucket.dto.CommentResponseDto;
import com.sparta.bucket.model.Comment;
import com.sparta.bucket.model.MockPost;
import com.sparta.bucket.repository.CommentRepository;
import com.sparta.bucket.repository.MockPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private MockPostRepository mockPostRepository;
    @Autowired
    private CommentRepository commentRepository;

    public CommentResponseDto postComment(Long postId, CommentRequestDto commentRequestDto) {
//        MockPost savedMockPost = mockPostRepository.findByMockPostId(postId).orElse(null);
        MockPost savedMockPost = mockPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));
        Comment comment = new Comment(savedMockPost, commentRequestDto);
        comment = commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public CommentResponseDto putComment(Long postId, Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("해당 댓글이 없습니다."));
        comment.setComment(commentRequestDto.getComment());
        comment.setEditCheck(true);
        comment = commentRepository.save(comment);

        return new CommentResponseDto(comment);

    }
}
