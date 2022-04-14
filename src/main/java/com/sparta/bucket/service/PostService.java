package com.sparta.bucket.service;

import com.sparta.bucket.dto.ImageDto;
import com.sparta.bucket.dto.PostAllGetResponseDto;
import com.sparta.bucket.dto.PostDto;
import com.sparta.bucket.dto.PostResponseDto;
import com.sparta.bucket.dto.*;
import com.sparta.bucket.model.Comment;
import com.sparta.bucket.model.Post;
import com.sparta.bucket.model.Todo;
import com.sparta.bucket.model.User;
import com.sparta.bucket.repository.PostRepository;
import com.sparta.bucket.repository.TodoRepository;
import com.sparta.bucket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    //(Write.html)게시글 작성
    @Transactional
    public PostResponseDto registerPost(PostDto postDtos, User user) {
        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();

        String title = postDtos.getTitle();
        String imageUrl = postDtos.getImageUrl();

        if (title == null) {
            throw new IllegalArgumentException("내용을 적어주세요.");
        } else if (imageUrl == null) {
            throw new IllegalArgumentException("이미지를 넣어주세요.");
        }

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
            TodoResponseDto todo = new TodoResponseDto(responsetodo.getId(), responsetodo.getContent(), responsetodo.getDone());
            todoResponseDtoList.add(todo);
        }


        //return 값 생성
        LocalDateTime createdTime = postList.getCreatedAt();
        return new PostResponseDto(title, imageUrl, createdTime, todoResponseDtoList);
    }

    //(Write.html)게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostDto postDtos) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 PostId 입니다.")
        );

        List<Todo> todoList = todoRepository.findAllByPostId(postId);
        for(Todo todo:todoList){
            todoRepository.deleteById(todo.getId());
        }

        post.update(postDtos);

        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();
        List<Todo> todos = new ArrayList<>();
        for(Todo responsetodo :postDtos.getTodo()){
            String content = responsetodo.getContent();
            Boolean done = responsetodo.getDone();
            Todo saveTodo = new Todo(content, done, post);
            todos.add(saveTodo);
            todoRepository.save(saveTodo);

            TodoResponseDto todo = new TodoResponseDto(saveTodo.getId(), saveTodo.getContent(), saveTodo.getDone());
            todoResponseDtoList.add(todo);
        }
        return new PostResponseDto(post.getTitle(), post.getImageUrl(), post.getCreatedAt(), todoResponseDtoList);
    }

    //(Write.html)이미지 저장
//    public ImageDto registerImage(MultipartFile file) throws IOException {
//
//        String projectPath = "/home/ubuntu/image";
//        UUID uuid = UUID.randomUUID();
//        String fileName = uuid + "_" + file.getOriginalFilename();
//        File saveFile = new File(projectPath, fileName);
//        file.transferTo(saveFile);
//
//        return new ImageDto("/image/" + fileName);
//    }

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

    public PostGetResponseDto getPost(Long postId) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(()->new NullPointerException("존재하지 않는 PostId 입니다."));
        Random rand = new Random();
        int likesNum = rand.nextInt(100);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();

        for (Todo todo : savedPost.getTodo()) {
            todoResponseDtoList.add(new TodoResponseDto(todo));
        }

        for (Comment comment : savedPost.getComment()) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }

        return new PostGetResponseDto(savedPost,todoResponseDtoList,likesNum,commentResponseDtoList);
    }

    public Slice<PostAllGetResponseDto> getPostsWithPage(Integer page, Integer size, String sortBy, Boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Slice<Post> slicePosts = postRepository.findAllBy(pageable);

        Random rand = new Random();
        int likesNum = rand.nextInt(100); // 0 ~ bound - 1

        Slice<PostAllGetResponseDto> postAllGetResponseDtoList = slicePosts.map(eachPost -> new PostAllGetResponseDto(eachPost, likesNum, eachPost.getComment().size()));

        return postAllGetResponseDtoList;
    }
}
