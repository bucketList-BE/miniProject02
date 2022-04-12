package com.sparta.bucket.service;

import com.sparta.bucket.dto.ImageDto;
import com.sparta.bucket.dto.PostAllGetResponseDto;
import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.ResponsePostDto;
import com.sparta.bucket.model.Post;
import com.sparta.bucket.model.Todo;
import com.sparta.bucket.model.User;
import com.sparta.bucket.repository.PostRepository;
import com.sparta.bucket.repository.TodoRepository;
import com.sun.nio.sctp.IllegalReceiveException;
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
import java.util.Random;

@RequiredArgsConstructor
@Service
public class PostService{

    private final PostRepository postRepository;
    private final TodoRepository todoRepository;

    //(Write.html)게시글 작성
    @Transactional
    public ResponsePostDto registerPost(List<PostDto> postDtos, User user) {
        ResponsePostDto savedPost = new ResponsePostDto();

        for (PostDto post : postDtos){
            String title = post.getTitle();
            String imageUrl = post.getImageUrl();

            if (title == null){
                throw new IllegalArgumentException("내용을 적어주세요.");
            } else if(imageUrl == null) {
                throw new IllegalArgumentException("이미지를 넣어주세요.");
            }


            //게시글 저장
            Post postList = new Post(title, imageUrl, user);
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
            savedPost.setImageUrl(imageUrl);
            savedPost.setTodo(todos);
            savedPost.setCreatedAt(createdTime);
        } return savedPost;
    }

    //(Write.html)게시글 수정
    @Transactional
    public ResponsePostDto updatePost(Long postId, PostDto postDtos) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 PostId 입니다.")
        );

        //게시글 수정 시 이미지가 다를 시 기존 이미지 삭제하기
        if(!(post.getImageUrl().equals(postDtos.getImageUrl()))){
            String projectPath = System.getProperty("user.dir") + "\\src\\main";
            projectPath += post.getImageUrl();
            try {
                Files.delete(Paths.get(projectPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        post.update(postDtos);//여기서 update 시에 user 가 포함이 안되도 되겠징???
        return new ResponsePostDto(post.getTitle(), post.getImageUrl(), post.getCreatedAt(), post.getTodo());
    }

    //(Write.html)이미지 저장
    public ImageDto registerImage(MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        return new ImageDto("/files/"+fileName);
    }

    public List<PostAllGetResponseDto> getAllPosts() {
        List<Post> allSavedPosts = postRepository.findAll();
        List<PostAllGetResponseDto> postAllGetResponseDtoList = new ArrayList<PostAllGetResponseDto>();
        Random rand = new Random();

        for (Post savedPost : allSavedPosts) {
            int likesNum = rand.nextInt(100); // 0 ~ bound - 1
            int commentsNum = savedPost.getComment().size();
            postAllGetResponseDtoList.add(new PostAllGetResponseDto(savedPost,likesNum,commentsNum));
        }

        return postAllGetResponseDtoList;
    }
}
