package com.example.wantedpreonboardingbackend.service;

import com.example.wantedpreonboardingbackend.domain.Post;
import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.dto.post.*;
import com.example.wantedpreonboardingbackend.repository.PostRepository;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
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

    public PostResponseDto updatePost(Long userId, Long id, PostWriteRequestDto postWriteRequestDto) {
        Post post = postRepository.findByIdAndUserId(id, userId);

        if (post == null) {
            throw new EmptyResultDataAccessException("해당하는 id의 게시글이 존재하지 않습니다. " + id,1);
        }


        post.setTitle(postWriteRequestDto.getTitle());
        post.setContent(postWriteRequestDto.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        // 변경 사항을 저장
        postRepository.save(post);

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

}