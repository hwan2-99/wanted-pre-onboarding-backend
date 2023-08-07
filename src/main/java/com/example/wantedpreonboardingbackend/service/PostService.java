package com.example.wantedpreonboardingbackend.service;

import com.example.wantedpreonboardingbackend.domain.Post;
import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.dto.post.*;
import com.example.wantedpreonboardingbackend.repository.PostRepository;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(PostWriteRequestDto postWriteRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        log.info("username:{}", user.getName());

        Post post = Post.builder()
                .title(postWriteRequestDto.getTitle())
                .content(postWriteRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return PostResponseDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .build();
    }

    public PostPaginationResponseDto getAllPosts(Pageable pageable) {
        Slice<Post> allPostsSliceBy = postRepository.findAllPostsSlice(pageable);
        List<PostsResponseDto> postResponseDtos = allPostsSliceBy.getContent().stream().map((PostsResponseDto::of)).collect(Collectors.toList());

        return PostPaginationResponseDto.builder()
                .posts(postResponseDtos)
                .numberOfPost(allPostsSliceBy.getNumberOfElements())
                .page(allPostsSliceBy.getPageable().getPageNumber())
                .build();
    }

    public PostDetailResponseDto getPostDetailById(Long id){
        Post post = postRepository.findPostById(id);
        return PostDetailResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .name(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .build();
    }

}