package com.example.wantedpreonboardingbackend.dto.post;

import com.example.wantedpreonboardingbackend.domain.Post;
import com.example.wantedpreonboardingbackend.domain.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostsResponseDto {
    private long id;
    private String title;
    private String content;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostsResponseDto of(Post post) {


        return PostsResponseDto.builder()
                .id(post.getId())
                .name(post.getUser().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
