package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.dto.JoinRequestDto;
import com.example.wantedpreonboardingbackend.dto.JoinResponseDto;
import com.example.wantedpreonboardingbackend.dto.LoginRequestDto;
import com.example.wantedpreonboardingbackend.dto.LoginTokenResponseDto;
import com.example.wantedpreonboardingbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<JoinResponseDto> signUp(@Valid @RequestBody JoinRequestDto joinRequestDto) {
        JoinResponseDto response = userService.join(joinRequestDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginTokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginTokenResponseDto response = userService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }
}
