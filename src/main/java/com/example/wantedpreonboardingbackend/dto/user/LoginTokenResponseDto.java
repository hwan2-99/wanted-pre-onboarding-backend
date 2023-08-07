package com.example.wantedpreonboardingbackend.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginTokenResponseDto {
    private String email;
    private String accessToken;
    private String refreshToken;
}
