package com.sparta.bucket.controller;

import com.sparta.bucket.model.Post;
import com.sparta.bucket.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ImageController {

    private final PostService postService;

    public ImageController(PostService postService) {
        this.postService = postService;
    }

    //파일 업로드 -실험중
    @GetMapping("/api/image")
    public String image(){
        return "image";
    }

    @PostMapping("/api/image")
    public String imageUpload(MultipartFile file) throws IOException {
        postService.registerImage(file);
        return "redirect:/image";
    }
}
