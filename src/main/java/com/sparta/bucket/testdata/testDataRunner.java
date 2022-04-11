package com.sparta.bucket.testdata;


import com.sparta.bucket.model.Post;
import com.sparta.bucket.model.User;
import com.sparta.bucket.repository.PostRepository;
import com.sparta.bucket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class testDataRunner implements ApplicationRunner {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        User user0= new User("test","test","1234");
        user0 = userRepository.save(user0);

//        Post post0 = new Post("안녕하세요1",user0);
//        Post post1 = new Post("안녕하세요2",user0);
//
//        postRepository.save(post0);
//        postRepository.save(post1);
    }
}
