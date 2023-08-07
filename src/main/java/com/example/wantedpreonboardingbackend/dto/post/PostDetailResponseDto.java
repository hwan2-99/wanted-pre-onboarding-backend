package com.example.wantedpreonboardingbackend.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDetailResponseDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private LocalDateTime createdAt;
}
