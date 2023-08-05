package com.example.wantedpreonboardingbackend.config.filter;

import com.example.wantedpreonboardingbackend.domain.User;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import com.example.wantedpreonboardingbackend.util.JWT.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String PREFIX_TOKEN = "Bearer ";

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        try {
            if (token != null) {
                if (!jwtProvider.isValidToken(token)) {
                    throw new ExpiredJwtException(null, null, "JWT 토큰이 만료됬습니다.");
                }
                String username = jwtProvider.getUsernameFromToken(token);
                log.info("name:{}",username);

                User user = userRepository.findByEmail(username);
                if (user == null) {
                    throw new RuntimeException("해당하는 유저가없음: " + username);
                }

                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication successful for user: {}", username);
            }
        }
        catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT 토큰이 만료되었습니다.");
            log.warn("JWT 토큰이 만료됬습니다.");
        }
        catch (SignatureException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("서명이 안된 토큰입니다.");
            log.warn("서명이 안된 토크입니다.");
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_TOKEN)) {
            return bearerToken.substring(PREFIX_TOKEN.length());
        }
        return null;
    }
}