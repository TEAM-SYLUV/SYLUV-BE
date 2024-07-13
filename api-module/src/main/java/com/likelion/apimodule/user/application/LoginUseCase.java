package com.likelion.apimodule.user.application;

import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.apimodule.user.dto.KakaoLoginRequest;
import com.likelion.coremodule.user.application.KakaoIdTokenDecodeService;
import com.likelion.coremodule.user.domain.User;
import com.likelion.coremodule.user.dto.LoginResponse;
import com.likelion.coremodule.user.dto.OidcDecodePayload;
import com.likelion.coremodule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final KakaoIdTokenDecodeService kakaoIdTokenDecodeService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LoginResponse kakaoLogin(final KakaoLoginRequest kakaoLoginRequest) {
        // ID 토큰으로 찾아온 유저 정보
        final OidcDecodePayload oidcDecodePayload = kakaoIdTokenDecodeService.getPayloadFromIdToken(kakaoLoginRequest.idToken());

        final User user = userRepository.findBySubId(oidcDecodePayload.sub())
                .orElseGet(() -> createNewKakaoUser(oidcDecodePayload));

        return new LoginResponse(jwtUtil.createJwtAccessToken(oidcDecodePayload.nickname(), oidcDecodePayload.sub()),
                jwtUtil.createJwtRefreshToken(oidcDecodePayload.nickname(), oidcDecodePayload.sub()));
    }

    private User createNewKakaoUser(final OidcDecodePayload oidcDecodePayload) {

        final User newUser = User.createSocialUser(oidcDecodePayload.sub());
        return userRepository.save(newUser);
    }
}
