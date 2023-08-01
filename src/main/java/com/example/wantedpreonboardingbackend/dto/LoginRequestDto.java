package com.example.wantedpreonboardingbackend.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class LoginRequestDto {
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Pattern(regexp = ".+@.+\\..+", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "패스워드는 필수 입력 항목입니다.")
    @Size(min = 8, message = "패스워드는 최소 8글자 이상이어야 합니다.")
    private String password;
}
