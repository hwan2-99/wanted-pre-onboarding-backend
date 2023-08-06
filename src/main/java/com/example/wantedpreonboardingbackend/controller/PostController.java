package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.domain.PrincipalDetails;
import com.example.wantedpreonboardingbackend.dto.PostResponseDto;
import com.example.wantedpreonboardingbackend.dto.PostWriteRequestDto;
import com.example.wantedpreonboardingbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<PostResponseDto> createPost(@AuthenticationPrincipal PrincipalDetails principalDetail, @RequestBody PostWriteRequestDto postWriteRequestDto) {

        Long userId = principalDetail.getUser().getId();
        log.info("principal:{}",principalDetail.getUser().getId());
        log.info("id:{}",userId);

        PostResponseDto responseDto = postService.createPost(postWriteRequestDto, userId);
        return ResponseEntity.ok(responseDto);
    }
}
