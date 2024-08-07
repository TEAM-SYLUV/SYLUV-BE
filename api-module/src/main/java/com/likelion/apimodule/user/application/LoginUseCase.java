package com.likelion.apimodule.user.application;

import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.apimodule.user.dto.KakaoLoginRequest;
import com.likelion.apimodule.user.dto.LoginInfo;
import com.likelion.coremodule.user.application.KakaoIdTokenDecodeService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import com.likelion.coremodule.user.dto.LoginAddResponse;
import com.likelion.coremodule.user.dto.LoginResponse;
import com.likelion.coremodule.user.dto.OidcDecodePayload;
import com.likelion.coremodule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final KakaoIdTokenDecodeService kakaoIdTokenDecodeService;
    private final UserQueryService userQueryService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LoginAddResponse kakaoLogin(final KakaoLoginRequest kakaoLoginRequest) {

        // ID 토큰으로 찾아온 유저 정보
        final OidcDecodePayload oidcDecodePayload = kakaoIdTokenDecodeService.getPayloadFromIdToken(kakaoLoginRequest.idToken());

        // User 존재 여부 확인 및 조회
        final Optional<User> optionalUser = userRepository.findBySubId(oidcDecodePayload.sub());
        final boolean existYn = optionalUser.isPresent();

        // 유저가 존재하지 않으면 새로운 유저 생성
        final User user = optionalUser.orElseGet(() -> createNewKakaoUser(oidcDecodePayload));

        // JWT 토큰 생성
        final String accessToken = jwtUtil.createJwtAccessToken(oidcDecodePayload.email(), oidcDecodePayload.sub());
        final String refreshToken = jwtUtil.createJwtRefreshToken(oidcDecodePayload.email(), oidcDecodePayload.sub());

        // 응답 객체 생성 및 반환
        return new LoginAddResponse(user.getName(), user.getPicture(), existYn, accessToken, refreshToken);
    }


    private User createNewKakaoUser(final OidcDecodePayload oidcDecodePayload) {

        final User newUser = User.createSocialUser(oidcDecodePayload.sub(), oidcDecodePayload.nickname(), oidcDecodePayload.picture(), oidcDecodePayload.email());
        return userRepository.save(newUser);
    }

    public LoginInfo getLoginInfo(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        return new LoginInfo(
                user.getName(),
                user.getPicture(),
                user.getEmail()
        );
    }

    @Transactional
    public LoginResponse reissueToken(String refreshToken) {

        return jwtUtil.reissueToken(refreshToken);
    }

    @Transactional
    public void logout(String refreshToken, String name) {

        jwtUtil.deleteToken(refreshToken);
        if (userRepository.findByName(name).isPresent()) userRepository.deleteByName(name);
    }
}
