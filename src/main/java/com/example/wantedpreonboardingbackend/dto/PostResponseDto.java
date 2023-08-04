package com.example.wantedpreonboardingbackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
}
