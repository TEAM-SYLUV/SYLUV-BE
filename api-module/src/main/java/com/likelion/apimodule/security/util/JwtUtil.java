package com.likelion.apimodule.security.util;

import com.likelion.commonmodule.exception.jwt.SecurityCustomException;
import com.likelion.commonmodule.exception.jwt.dto.JwtPair;
import com.likelion.commonmodule.redis.util.RedisUtil;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.likelion.commonmodule.exception.jwt.SecurityErrorCode.INVALID_TOKEN;
import static com.likelion.commonmodule.exception.jwt.SecurityErrorCode.TOKEN_EXPIRED;

@Component
@Slf4j
public class JwtUtil {

    private static final String AUTHORITIES_CLAIM_NAME = "auth";

    private final SecretKey secretKey;
    private final Long accessExpMs;
    private final Long refreshExpMs;
    private final RedisUtil redisUtil;

    public JwtUtil(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.token.access-expiration-time}") Long access,
            @Value("${security.jwt.token.refresh-expiration-time}") Long refresh,
            RedisUtil redis) {

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        accessExpMs = access;
        refreshExpMs = refresh;
        redisUtil = redis;
    }

    public String createJwtAccessToken(String nickname, String subId) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(accessExpMs);

        return Jwts.builder()
                .header()
                .add("alg", "HS256")
                .add("typ", "JWT")
                .and()
                .subject(subId)
                .claim("nickname", nickname)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public String createJwtRefreshToken(String nickname, String subId) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plusMillis(refreshExpMs);

        String refreshToken = Jwts.builder()
                .header()
                .add("alg", "HS256")
                .add("typ", "JWT")
                .and()
                .subject(subId)
                .claim("nickname", nickname)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();

        redisUtil.saveAsValue(
                subId + "_refresh_token",
                refreshToken,
                refreshExpMs,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
    }

//    public JwtPair reissueToken(String refreshToken) {
//        try {
//            validateRefreshToken(refreshToken);
//            log.info("[*] Valid RefreshToken");
//
//            return new JwtPair(
//                    createJwtAccessToken(tempCustomUserDetails),
//                    createJwtRefreshToken(tempCustomUserDetails)
//            );
//        } catch (IllegalArgumentException iae) {
//            throw new SecurityCustomException(INVALID_TOKEN, iae);
//        } catch (ExpiredJwtException eje) {
//            throw new SecurityCustomException(TOKEN_EXPIRED, eje);
//        }
//    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.warn("[*] No token in req");
            return null;
        }

        log.info("[*] Token exists");
        return authorization.split(" ")[1];
    }

    public void validateRefreshToken(String refreshToken) {
        // refreshToken 유효성 검증
        String email = getEmail(refreshToken);

        //redis에 refreshToken 있는지 검증
        if (!redisUtil.hasKey(email + "_refresh_token")) {
            log.warn("[*] case : Invalid refreshToken");
            throw new SecurityCustomException(INVALID_TOKEN);
        }
    }

    public Long getId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    public String getAuthority(String token) {
        return getClaims(token).get(AUTHORITIES_CLAIM_NAME, String.class);
    }

    public Boolean isExpired(String token) {
        // 여기서 토큰 형식 이상한 것도 걸러짐
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }

    public Long getExpTime(String token) {
        return getClaims(token).getExpiration().getTime();
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new SecurityCustomException(INVALID_TOKEN, e);
        }
    }
}
