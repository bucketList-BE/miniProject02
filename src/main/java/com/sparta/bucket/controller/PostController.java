package com.sparta.bucket.controller;

import com.sparta.bucket.dto.ImageDto;
import com.sparta.bucket.dto.PostAllGetResponseDto;
import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.PostGetResponseDto;
import com.sparta.bucket.dto.PostResponseDto;
import com.sparta.bucket.security.UserDetailsImpl;
import com.sparta.bucket.service.PostService;
import com.sparta.bucket.service.S3Service;
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
    private final S3Service s3Service;

    //게시글 작성
    @PostMapping("/api/post")
    public PostResponseDto createPost(
            @RequestPart PostDto postDtos,
            @RequestPart MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String imagePath = s3Service.upload(file);
        postDtos.setImageUrl(imagePath);
        return postService.registerPost(postDtos, userDetails.getUser());
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public PostResponseDto updatePost(
            @PathVariable Long postId,
            @RequestPart PostDto postDtos,
            @RequestPart MultipartFile file
    ) {
        String imagePath = s3Service.upload(file, postDtos.getImageUrl());
        postDtos.setImageUrl(imagePath);
        return postService.updatePost(postId, postDtos);
    }

//    //이미지 저장
//    @PostMapping("/api/image")
//    public ImageDto imageUpload(@RequestParam("file") MultipartFile file) throws IOException {
//        return postService.registerImage(file);
//    }

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
