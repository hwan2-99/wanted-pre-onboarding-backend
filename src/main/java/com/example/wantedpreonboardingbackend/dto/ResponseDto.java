package com.example.wantedpreonboardingbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto {
    private Long id;
    private String email;
    private String name;
}
