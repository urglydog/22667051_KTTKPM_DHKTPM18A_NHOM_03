package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value; // Dùng bản này của Spring
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority; // Import cái này
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder; // Cần cái này
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
@Service
public class TokenService {
    // 1. Khai báo biến encoder (Spring Security sẽ dùng JwtEncoder bạn tạo bên SecurityConfig)
    private final JwtEncoder encoder;

    // 2. Tạo Constructor để Spring tự động "tiêm" encoder vào đây
    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        // Lấy các quyền (roles) của user để đưa vào token
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS)) // Token hết hạn sau 1 giờ
                .subject(authentication.getName())
                .claim("scope", scope) // Lưu Role vào đây để phân quyền
                .build();

        // Đoạn này cần thêm JwtEncoder bean trong SecurityConfig để chạy
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
