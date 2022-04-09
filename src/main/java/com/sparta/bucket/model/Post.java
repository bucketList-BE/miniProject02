package com.sparta.bucket.model;

import com.sparta.bucket.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String filename;

    @Column
    private String filepath;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Todo> todo;


    public Post(String title, User user){
        this.title = title;
        this.user = user;
    }

    public void update(PostDto postDtos) {
        this.title = postDtos.getTitle();
        this.todo = postDtos.getTodo();
    }
}
