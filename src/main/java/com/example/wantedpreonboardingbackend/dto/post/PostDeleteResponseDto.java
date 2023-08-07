package com.example.wantedpreonboardingbackend.dto.post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDeleteResponseDto {
    private Long id;
    private String message;
}
