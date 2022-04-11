package com.sparta.bucket.service;

import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.ResponsePostDto;
import com.sparta.bucket.model.Post;
import com.sparta.bucket.model.Todo;
import com.sparta.bucket.model.User;
import com.sparta.bucket.repository.PostRepository;
import com.sparta.bucket.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService{

    private final PostRepository postRepository;
    private final TodoRepository todoRepository;

    //(Write.html)게시글 작성
    @Transactional
    public ResponsePostDto registerPost(List<PostDto> postDtos, User user) //, User user)
    {

        ResponsePostDto savedPost = new ResponsePostDto();


        for (PostDto post : postDtos){
            String title = post.getTitle();

            if (title == null){
                throw new IllegalArgumentException("내용을 적어주세요.");
            }

            //게시글 저장
            Post postList = new Post(title, user);  //, user
            postRepository.save(postList);

            //todoList 저정
            List<Todo> todoList = post.getTodo();
            List<Todo> todos = new ArrayList<>();
            for(Todo todo : todoList){
                String content = todo.getContent();
                Boolean done = todo.getDone();

                if (content == null){
                    throw new IllegalArgumentException("내용을 적어주세요.");
                }

                Todo saveTodo = new Todo(content, done, postList);
                todos.add(saveTodo);
                todoRepository.save(saveTodo);
            }
            //return 값 생성
            LocalDateTime createdTime = postList.getCreatedAt();
            savedPost.setTitle(title);
            savedPost.setTodo(todos);
            savedPost.setCreatedAt(createdTime);


        } return savedPost;
    }

    //(Write.html)게시글 수정
    @Transactional
    public PostDto updatePost(Long postId, PostDto postDtos) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 PostId 입니다.")
        );
        post.update(postDtos); //여기서 update 시에 user 가 포함이 안되도 되는지...?
        PostDto postDto = new PostDto(post.getTitle(), post.getTodo());
        return postDto;
    }

    //이미지 저장 실험중...
    public void registerImage(MultipartFile file) throws IOException {
        Post post = new Post();

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);


        post.setTitle("울랄라");
        post.setUser(new User("울랄라"));
        post.setFilepath("/files/"+fileName);

        postRepository.save(post);
    }
}
