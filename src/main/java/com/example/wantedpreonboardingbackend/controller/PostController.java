package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.dto.PostWriteRequestDto;
import com.example.wantedpreonboardingbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    @PostMapping("/create")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostWriteRequestDto postWriteRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new BadCredentialsException("로그인된 사용자 정보를 가져올 수 없습니다.");
        }

        Long userId = ((User) userDetails).getId();
        PostResponseDto responseDto = postService.createPost(postWriteRequestDto, userId);
        return ResponseEntity.ok(responseDto);
    }
}
