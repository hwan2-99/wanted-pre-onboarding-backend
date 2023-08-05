package com.example.wantedpreonboardingbackend.service;

import com.example.wantedpreonboardingbackend.domain.Post;
import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.dto.PostWriteRequestDto;
import com.example.wantedpreonboardingbackend.repository.PostRepository;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(PostWriteRequestDto postWriteRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        log.info("username:{}",user.getName());

        Post post = Post.builder()
                .title(postWriteRequestDto.getTitle())
                .content(postWriteRequestDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return PostResponseDto.builder()
                .id((long) savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .build();
    }
}
