package com.example.wantedpreonboardingbackend.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostPaginationResponseDto {
    List<PostsResponseDto> posts;
    private int numberOfPost;
    private long page;

}
