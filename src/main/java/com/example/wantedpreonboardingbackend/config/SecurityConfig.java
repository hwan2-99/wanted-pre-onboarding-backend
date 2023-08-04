package com.example.wantedpreonboardingbackend.config;

import com.example.wantedpreonboardingbackend.config.filter.JwtAccessDeniedHandler;
import com.example.wantedpreonboardingbackend.config.filter.JwtAuthenticationEntryPoint;
import com.example.wantedpreonboardingbackend.config.filter.JwtAuthenticationFilter;
import com.example.wantedpreonboardingbackend.repository.UserRepository;
import com.example.wantedpreonboardingbackend.util.JWT.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public SecurityConfig(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/post/**").authenticated() // 게시글 작성은 인증된 사용자만 접근 가능
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, userRepository), UsernamePasswordAuthenticationFilter.class)
                .httpBasic();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
