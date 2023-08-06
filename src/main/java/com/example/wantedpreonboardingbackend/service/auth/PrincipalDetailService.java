package com.example.wantedpreonboardingbackend.service.auth;

import com.example.wantedpreonboardingbackend.domain.PrincipalDetails;
import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("testtesttesttesttesttesttesttesttest");
        User user = userRepository.findByEmail(email);
        log.info("user:{}",user.getName());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new PrincipalDetails(user);
    }
}
