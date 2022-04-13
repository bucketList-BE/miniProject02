package com.sparta.bucket.controller;

import com.sparta.bucket.dto.ImageDto;
import com.sparta.bucket.dto.PostAllGetResponseDto;
import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.PostGetResponseDto;
import com.sparta.bucket.dto.PostResponseDto;
import com.sparta.bucket.security.UserDetailsImpl;
import com.sparta.bucket.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    //게시글 작성
    @PostMapping("/api/post")
    public PostResponseDto createPost(
            @RequestBody PostDto postDtos,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        System.out.println("user의 정보를 가져옵니다.^^" + userDetails.getUser());
        return postService.registerPost(postDtos, userDetails.getUser());
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public PostResponseDto updatePost(
            @PathVariable Long postId,
            @RequestBody PostDto postDtos
    ) {
        return postService.updatePost(postId, postDtos);
    }

    //이미지 저장
    @PostMapping("/api/image")
    public ImageDto imageUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return postService.registerImage(file);
    }

    //전체 게시글 조회
    @GetMapping("/api/posts")
    public List<PostAllGetResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    //게시글 조회
    @GetMapping("/api/post/{postId}")
    public PostGetResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }
}
