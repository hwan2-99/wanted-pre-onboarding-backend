package com.example.wantedpreonboardingbackend.controller;

import com.example.wantedpreonboardingbackend.domain.PrincipalDetails;
import com.example.wantedpreonboardingbackend.dto.post.*;
import com.example.wantedpreonboardingbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/all")
    public ResponseEntity<PostPaginationResponseDto> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page,size);

        PostPaginationResponseDto responseDto = postService.getAllPosts(pageable);

        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable Long id) {
        PostDetailResponseDto postDetail = postService.getPostDetailById(id);
        return ResponseEntity.ok(postDetail);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@AuthenticationPrincipal PrincipalDetails principalDetail, @PathVariable Long id, @RequestBody PostWriteRequestDto postWriteRequestDto) {

        Long userId = principalDetail.getUser().getId();

        PostResponseDto updatedPost = postService.updatePost(userId, id, postWriteRequestDto);

        return ResponseEntity.ok(updatedPost);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<PostDeleteResponseDto> deletePost(@AuthenticationPrincipal PrincipalDetails principalDetail,@PathVariable Long id) {
        Long userId = principalDetail.getUser().getId();
        PostDeleteResponseDto responseDto = postService.deletePost(id, userId);
        return ResponseEntity.ok(responseDto);
    }
}
