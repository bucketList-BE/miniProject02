package com.sparta.bucket.dto;

import com.sparta.bucket.model.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostGetResponseDto {
    private Long postId;
    private String userNickname;
    private String title;
    private String imageUrl;
    private Integer likesNum;
    private Integer commentsNum;
    private List<TodoResponseDto> todo;
    private List<CommentResponseDto> comments;


    public PostGetResponseDto(Post savedPost, List<TodoResponseDto> todoResponseDtoList, Integer likesNum, List<CommentResponseDto> commentResponseDtoList) {
        this.postId = savedPost.getId();
        this.userNickname = savedPost.getUser().getNickname();
        this.title = savedPost.getTitle();
        this.imageUrl = savedPost.getImageUrl();
        this.likesNum = likesNum;
        this.todo = todoResponseDtoList;
        this.comments = commentResponseDtoList;
        this.commentsNum = commentResponseDtoList.size();
    }

}
