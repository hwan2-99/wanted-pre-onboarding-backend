package com.example.wantedpreonboardingbackend.user;

import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.dto.user.JoinRequestDto;
import com.example.wantedpreonboardingbackend.dto.user.JoinResponseDto;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import com.example.wantedpreonboardingbackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 회원가입() {
        // Given
        JoinRequestDto joinRequestDto = new JoinRequestDto();
        joinRequestDto.setEmail("test@example.com");
        joinRequestDto.setPassword("password");
        joinRequestDto.setName("Test User");

        User user = User.builder()
                .email(joinRequestDto.getEmail())
                .password(joinRequestDto.getPassword())
                .name(joinRequestDto.getName())
                .build();

        when(userRepository.findByEmail(joinRequestDto.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(joinRequestDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        JoinResponseDto responseDto = userService.join(joinRequestDto);

        // Then
        Assertions.assertEquals(user.getEmail(), responseDto.getEmail());
        Assertions.assertEquals(user.getName(), responseDto.getName());
        // Add more assertions as needed
    }
    @Test
    public void 회원가입_비밀번호_실패() {
//        // Given
//        JoinRequestDto joinRequestDto = new JoinRequestDto();
//        joinRequestDto.setEmail("test@example.com");
//        joinRequestDto.setPassword("12");
//        joinRequestDto.setName("Test User");
//
//        if(joinRequestDto.getPassword().length() < 8){
//            IllegalStateException e = assertThrows(IllegalStateException.class,
//                    () -> userService.join(joinRequestDto));//예외가 발생해야 한다.
//        }
//
//        User user = User.builder()
//                .email(joinRequestDto.getEmail())
//                .password(joinRequestDto.getPassword())
//                .name(joinRequestDto.getName())
//                .build();
//
//        when(userRepository.findByEmail(joinRequestDto.getEmail())).thenReturn(null);
//        when(passwordEncoder.encode(joinRequestDto.getPassword())).thenReturn("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        // When
//        JoinResponseDto responseDto = userService.join(joinRequestDto);
//
//        // Then
//        Assertions.assertEquals(user.getEmail(), responseDto.getEmail());
//        Assertions.assertEquals(user.getName(), responseDto.getName());
    }

}


