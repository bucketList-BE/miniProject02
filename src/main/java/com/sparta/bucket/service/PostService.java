package com.sparta.bucket.service;

import com.sparta.bucket.dto.*;
import com.sparta.bucket.model.Comment;
import com.sparta.bucket.model.Post;
import com.sparta.bucket.model.Todo;
import com.sparta.bucket.model.User;
import com.sparta.bucket.repository.PostRepository;
import com.sparta.bucket.repository.TodoRepository;
import com.sparta.bucket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    //(Write.html)게시글 작성
    @Transactional
    public ResponsePostDto registerPost(PostDto postDtos) {   //, User user
        ResponsePostDto savedPost = new ResponsePostDto();
        List<ResponseTodoDto> responsetodoList = new ArrayList<>();

        String title = postDtos.getTitle();
        String imageUrl = postDtos.getImageUrl();

        if (title == null) {
            throw new IllegalArgumentException("내용을 적어주세요.");
        } else if (imageUrl == null) {
            throw new IllegalArgumentException("이미지를 넣어주세요.");
        }


        //게시글 저장
        User user = userRepository.findById(1L).orElseThrow(
                ()-> new IllegalArgumentException("찾는 유저가 없습니다.")
        );

        Post postList = new Post(title, imageUrl, user);
        postRepository.save(postList);

        //todoList 저정
        List<Todo> todoList = postDtos.getTodo();
        List<Todo> todos = new ArrayList<>();
        for (Todo todo : todoList) {
            String content = todo.getContent();
            Boolean done = todo.getDone();

            if (content == null) {
                throw new IllegalArgumentException("내용을 적어주세요.");
            }

            Todo saveTodo = new Todo(content, done, postList);
            todos.add(saveTodo);
            todoRepository.save(saveTodo);
        }

        for(Todo responsetodo :todos){
            ResponseTodoDto todo = new ResponseTodoDto(responsetodo.getId(), responsetodo.getContent(), responsetodo.getDone());
            responsetodoList.add(todo);
        }


        //return 값 생성
        LocalDateTime createdTime = postList.getCreatedAt();
        savedPost.setTitle(title);
        savedPost.setImageUrl(imageUrl);
        savedPost.setTodo(responsetodoList);
        savedPost.setCreatedAt(createdTime);

        return savedPost;
    }

    //(Write.html)게시글 수정
    @Transactional
    public ResponsePostDto updatePost(Long postId, PostDto postDtos) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 PostId 입니다.")
        );

        List<Todo> todoList = todoRepository.findAllByPostId(postId);
        for(Todo todo:todoList){
            todoRepository.deleteById(todo.getId());
        }

        //게시글 수정 시 이미지가 다를 시 기존 이미지 삭제하기
        if (!(post.getImageUrl().equals(postDtos.getImageUrl()))) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main";
            //서버연결시 변경하기
//            String projectPath = "/home/ubuntu/image";

            projectPath += post.getImageUrl();
            try {
                Files.delete(Paths.get(projectPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        post.update(postDtos);//여기서 update 시에 user 가 포함이 안되도 되겠징???
        post.update(postDtos);

        List<ResponseTodoDto> responsetodoList = new ArrayList<>();
        List<Todo> todos = new ArrayList<>();
        for(Todo responsetodo :postDtos.getTodo()){
            String content = responsetodo.getContent();
            Boolean done = responsetodo.getDone();
            Todo saveTodo = new Todo(content, done, post);
            todos.add(saveTodo);
            todoRepository.save(saveTodo);

            ResponseTodoDto todo = new ResponseTodoDto(saveTodo.getId(), saveTodo.getContent(), saveTodo.getDone());
            responsetodoList.add(todo);
        }

        return new ResponsePostDto(post.getTitle(), post.getImageUrl(), post.getCreatedAt(), responsetodoList);
    }

    //(Write.html)이미지 저장
    public ImageDto registerImage(MultipartFile file) throws IOException {

//        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        String projectPath = "/home/ubuntu/image";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

//        return new ImageDto("/files/"+fileName);

        return new ImageDto("/image/" + fileName);
    }
}
