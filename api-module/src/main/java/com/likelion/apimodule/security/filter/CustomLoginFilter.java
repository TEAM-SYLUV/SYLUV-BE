package com.likelion.apimodule.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.apimodule.user.dto.KakaoLoginRequest;
import com.likelion.commonmodule.redis.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public Authentication attemptAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws AuthenticationException {
        logger.info("[*] Kakao Login Filter");

        KakaoLoginRequest kakaoLoginRequest;
        try {
            kakaoLoginRequest = getKakaoLoginRequest(request);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Error occurred while parsing request body");
        }

        logger.info("[*] Request Body : " + kakaoLoginRequest);

        String idToken = kakaoLoginRequest.idToken();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                idToken, null);

        return authenticationManager.authenticate(authToken);
    }

//    @Override
//    protected void successfulAuthentication(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain chain,
//            @NonNull Authentication authentication) throws IOException {
//        logger.info("[*] Kakao Login Success");
//
//        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
//
//        // Generate JWT tokens
//        JwtPair jwtPair = new JwtPair(
//                jwtUtil.createJwtAccessToken(customUserDetails),
//                jwtUtil.createJwtRefreshToken(customUserDetails)
//        );
//
//        // Return JWT tokens in the response
//        HttpResponseUtil.setSuccessResponse(response, HttpStatus.CREATED, jwtPair);
//    }

//    @Override
//    protected void unsuccessfulAuthentication(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull AuthenticationException failed) throws IOException {
//        logger.info("[*] Kakao Login Fail");
//
//        // Handle unsuccessful authentication
//        String errorMessage = "Kakao authentication failed";
//        HttpResponseUtil.setErrorResponse(
//                response, HttpStatus.UNAUTHORIZED,
//                ApiResponse.onFailure(
//                        HttpStatus.UNAUTHORIZED.name(),
//                        errorMessage
//                )
//        );
//    }

    private KakaoLoginRequest getKakaoLoginRequest(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request.getInputStream(), KakaoLoginRequest.class);
    }
}
