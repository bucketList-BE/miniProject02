package com.sparta.bucket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Post> post;

    public User(String username, String nickname, String enPassword) {
        this.username = username;
        this.nickname = nickname;
        this.password = enPassword;
    }
    //이미지저장 실험중 ... 성공 시 지우기
    public User(String username){
        this.username = username;
    }
}
