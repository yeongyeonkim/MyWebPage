package com.kyy.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private long expiredToken = 1000L * 60 * 60; // 1시간

}
