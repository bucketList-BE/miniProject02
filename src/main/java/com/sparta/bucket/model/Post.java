package com.sparta.bucket.model;

import com.sparta.bucket.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Todo> todo;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;


    public void update(PostDto postDtos) {
        this.title = postDtos.getTitle();
        this.imageUrl = postDtos.getImageUrl();
        this.todo = postDtos.getTodo();
    }

    public Post(String title, String imageUrl, User user){
        this.title = title;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
