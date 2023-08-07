package com.example.wantedpreonboardingbackend.dto.post;

import com.example.wantedpreonboardingbackend.domain.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;


}
