package com.example.wantedpreonboardingbackend.service;

import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.dto.JoinRequestDto;
import com.example.wantedpreonboardingbackend.dto.JoinResponseDto;
import com.example.wantedpreonboardingbackend.dto.LoginRequestDto;
import com.example.wantedpreonboardingbackend.dto.LoginTokenResponseDto;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import com.example.wantedpreonboardingbackend.util.JWT.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    public JoinResponseDto join(JoinRequestDto joinRequestDto){

        // 회원가입 이메일 중복검사
        User duplicateUser = userRepository.findByEmail(joinRequestDto.getEmail());
        if(duplicateUser != null){
            throw new IllegalArgumentException("존재하는 이메일입니다.");
        }

        User user = joinRequestDto.toEntity();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.encodePassword(encodedPassword);
        userRepository.save(user);

        return JoinResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public LoginTokenResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        log.info("이메일: {}",loginRequestDto.getEmail());

        if (user == null) {
            throw new BadCredentialsException("해당 이메일에 해당하는 유저가 없습니다.: " + loginRequestDto.getEmail());
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            log.info("요청 비밀번호:{}",loginRequestDto.getPassword());
            log.info("비밀번호:{}",user.getPassword());
            throw new BadCredentialsException("비밀번호가 틀립니다.");
        }

        // 사용자 인증이 성공하면 JWT 토큰 생성
        String accessToken = jwtProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtProvider.createRefreshToken();

        return LoginTokenResponseDto.builder()
                .email(user.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
