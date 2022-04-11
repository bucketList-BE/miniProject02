package com.sparta.bucket.dto;

import com.sparta.bucket.model.Post;
import lombok.Getter;

@Getter
public class PostAllGetResponseDto {
    private Long postId;
    private String userNickname;
    private String title;
    private String imageUrl;
    private Integer likesNum;
    private Integer commentsNum;

    public PostAllGetResponseDto(Post savedPost, Integer likesNum, Integer commentsNum) {
        this.postId = savedPost.getId();
        this.userNickname = savedPost.getUser().getNickname();
        this.title = savedPost.getTitle();
//        this.imageUrl = savedPost.getImageUrl();
        this.imageUrl = "URL";
        this.likesNum = likesNum;
        this.commentsNum = commentsNum;
    }
}
