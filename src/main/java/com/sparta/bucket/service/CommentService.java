package com.sparta.bucket.service;

import com.sparta.bucket.dto.CommentRequestDto;
import com.sparta.bucket.dto.CommentResponseDto;
import com.sparta.bucket.model.Comment;
import com.sparta.bucket.model.MockPost;
import com.sparta.bucket.repository.CommentRepository;
import com.sparta.bucket.repository.MockPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.MimeTable;

@Service
public class CommentService {
    @Autowired
    private MockPostRepository mockPostRepository;
    @Autowired
    private CommentRepository commentrepository;

    public CommentResponseDto postComment(Long postId, CommentRequestDto commentRequestDto) {
//        MockPost savedMockPost = mockPostRepository.findByMockPostId(postId).orElse(null);
        MockPost savedMockPost = mockPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));
        Comment comment = new Comment(savedMockPost, commentRequestDto);
        comment = commentrepository.save(comment);

        return new CommentResponseDto(comment);
    }
}
