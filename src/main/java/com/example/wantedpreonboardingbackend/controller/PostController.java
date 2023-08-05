package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.domain.PrincipalDetails;
import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.dto.PostWriteRequestDto;
import com.example.wantedpreonboardingbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostWriteRequestDto postWriteRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException("로그인된 사용자 정보가 없습니다.");
        }

        Object principal = authentication.getPrincipal();
        log.info("hi:{}",principal);


        PrincipalDetails principalDetails = (PrincipalDetails) principal;
        User user = principalDetails.getUser();
        Long userId = user.getId();

        PostResponseDto responseDto = postService.createPost(postWriteRequestDto, userId);
        return ResponseEntity.ok(responseDto);
    }
}
