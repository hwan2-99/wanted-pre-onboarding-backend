package com.example.wantedpreonboardingbackend.dto.post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostWriteRequestDto {
    private String title;
    private String content;

}
