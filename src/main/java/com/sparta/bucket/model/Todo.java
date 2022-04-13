package com.sparta.bucket.model;

import com.sparta.bucket.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Todo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean done;

    @ManyToOne
    @JoinColumn(name="POST_ID", nullable = false)
    private Post post;


    public Todo(String content, Boolean done, Post post){
        this.content = content;
        this.done = done;
        this.post = post;
    }
}
