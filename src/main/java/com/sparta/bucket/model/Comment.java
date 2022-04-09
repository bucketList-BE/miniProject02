package com.sparta.bucket.model;


import com.sparta.bucket.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long commentId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MockPost mockPost;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;

    private boolean editCheck;

    public Comment(MockPost mockPost, CommentRequestDto commentRequestDto) {
        this.mockPost = mockPost;
        this.comment = commentRequestDto.getComment();
        this.username = commentRequestDto.getUsername();
    }
}
