package com.sparta.bucket.testdata;


import com.sparta.bucket.model.Post;
import com.sparta.bucket.model.User;
import com.sparta.bucket.repository.PostRepository;
import com.sparta.bucket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class testDataRunner implements ApplicationRunner {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        String enPassword = passwordEncoder.encode("1234");
        // 테스트 User 생성
        User user = new User("test1@sp.com", "test1", enPassword);
        userRepository.save(user);
    }
}
