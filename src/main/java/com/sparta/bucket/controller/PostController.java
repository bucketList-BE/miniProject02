package com.sparta.bucket.controller;

import com.sparta.bucket.dto.ImageDto;
import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.ResponsePostDto;
import com.sparta.bucket.model.User;
import com.sparta.bucket.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    //게시글 작성
    @PostMapping("/api/post")
    public ResponsePostDto createPost(
            @RequestBody List<PostDto> postDtos,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
            User user
    ) {
        return postService.registerPost(postDtos, user);//userDetails.getUser);
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
}
