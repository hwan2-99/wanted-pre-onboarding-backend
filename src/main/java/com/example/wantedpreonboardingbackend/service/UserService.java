package com.example.wantedpreonboardingbackend.service;

import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.dto.JoinRequestDto;
import com.example.wantedpreonboardingbackend.dto.ResponseDto;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseDto join(JoinRequestDto joinRequestDto){

        // 회원가입 이메일 중복검사
        User duplicateUser = userRepository.findByEmail(joinRequestDto.getEmail());
        if(duplicateUser != null){
            throw new IllegalArgumentException("존재하는 이메일입니다.");
        }

        User user = joinRequestDto.toEntity();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.encodePassword(encodedPassword);
        userRepository.save(user);

        return ResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }


}
