package com.sparta.bucket.controller;

import com.sparta.bucket.dto.PostAllGetResponseDto;
import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.PostGetResponseDto;
import com.sparta.bucket.dto.ResponsePostDto;
import com.sparta.bucket.model.User;
import com.sparta.bucket.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@ResponseStatus(HttpStatus.OK)
public class PostController {

    private final PostService postService;

    //게시글 작성
    @PostMapping("/api/posts")
    public ResponsePostDto createPost(
            @RequestBody List<PostDto> postDtos,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
            User user
    ) {
        return postService.registerPost(postDtos, user);//userDetails.getUser);
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public PostDto updatePost(
            @PathVariable Long postId,
            @RequestBody PostDto postDtos
    ) {
        return postService.updatePost(postId, postDtos);
    }

    @GetMapping("/api/posts")
    public List<PostAllGetResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/api/post/{postId}")
    public PostGetResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }
}
