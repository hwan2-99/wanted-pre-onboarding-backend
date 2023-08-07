package com.example.wantedpreonboardingbackend.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinResponseDto {
    private Long id;
    private String email;
    private String name;
}
