package com.sparta.bucket.testdata;


import com.sparta.bucket.model.MockPost;
import com.sparta.bucket.repository.MockPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class testDataRunner implements ApplicationRunner {

    @Autowired
    private MockPostRepository mockPostRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        MockPost mockPost0 = new MockPost();
        MockPost mockPost1 = new MockPost();

        mockPostRepository.save(mockPost0);
        mockPostRepository.save(mockPost1);
    }
}
