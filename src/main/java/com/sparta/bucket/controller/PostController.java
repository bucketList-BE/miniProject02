package com.sparta.bucket.controller;

import com.sparta.bucket.dto.ImageDto;
import com.sparta.bucket.dto.PostAllGetResponseDto;
import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.PostGetResponseDto;
import com.sparta.bucket.dto.PostResponseDto;
import com.sparta.bucket.model.Post;
import com.sparta.bucket.security.UserDetailsImpl;
import com.sparta.bucket.service.PostService;
import com.sparta.bucket.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
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

    //만약에 안된다고 하면은 수정이 살려야지~~~~~~~~~
//    Post post = postRepository.findById(postId).orElseThrow(
//            () -> new IllegalArgumentException("해당하는 포스트가 없습니다.")
//    );
//
//        if(postDtos.getImageUrl() == null){
//        String exImageUrl = post.getImageUrl();
//        postDtos.setImageUrl(exImageUrl);
//    } else{
//        String imagePath = s3Service.upload(file, postDtos.getImageUrl());
//        postDtos.setImageUrl(imagePath);
//    }

    //전체 게시글 조회
    @GetMapping("/api/posts")
    public List<PostAllGetResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/api/postsWithPage")
    public Slice<PostAllGetResponseDto> getPostsWithPage(
            @RequestParam(required=false) Integer page,
            @RequestParam(required=false) Integer size,
            @RequestParam(required=false) String sortBy,
            @RequestParam(required=false) Boolean isAsc
            ) {
        if(isNotNullAllParam(page,size,sortBy,isAsc)){
            page = page - 1; // page starts at 0
            return postService.getPostsWithPage(page, size, sortBy, isAsc);
        }
        else
            throw new IllegalArgumentException("페이지의 모든 요소가 필요합니다.");
    }

    private boolean isNotNullAllParam(Integer page, Integer size, String sortBy, Boolean isAsc) {
        return (page != null) && (size != null) && (sortBy != null) && (isAsc != null);
    }

    //게시글 조회
    @GetMapping("/api/post/{postId}")
    public PostGetResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }
}
