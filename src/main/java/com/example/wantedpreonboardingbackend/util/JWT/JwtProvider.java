package com.example.wantedpreonboardingbackend.util.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    @Value("${jwt.token.secret-key}")
    private String secretKey;

    public Token createToken(String payload) {
        return Token.builder()
                .accessToken(createAccessToken(payload))
                .refreshToken(createRefreshToken())
                .build();
    }
    public String createAccessToken(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date validity = new Date(now.getTime());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String createRefreshToken() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String randomString = new String(array, StandardCharsets.UTF_8);

        Claims claims = Jwts.claims().setSubject(randomString);
        Date now = new Date();
        Date validity = new Date(now.getTime());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
