package com.sparta.bucket.controller;

import com.sparta.bucket.dto.ImageDto;
import com.sparta.bucket.dto.PostAllGetResponseDto;
import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.PostGetResponseDto;
import com.sparta.bucket.dto.ResponsePostDto;
import com.sparta.bucket.model.User;
import com.sparta.bucket.service.PostService;
import lombok.RequiredArgsConstructor;
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
    public ResponsePostDto createPost(
            @RequestBody PostDto postDtos
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//            User user
    ) {
        return postService.registerPost(postDtos); // , useruserDetails.getUser);
    }

    //게시글 조회
    @GetMapping("/api/post/{postId}")
    public PostDto findPost(
            @PathVariable Long postId
    ) {
        return postService.findPost(postId);
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public ResponsePostDto updatePost(
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

    @GetMapping("/api/posts")
    public List<PostAllGetResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/api/post/{postId}")
    public PostGetResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }
}
