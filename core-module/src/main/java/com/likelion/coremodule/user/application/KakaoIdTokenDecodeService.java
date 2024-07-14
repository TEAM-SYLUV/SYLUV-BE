package com.likelion.coremodule.user.application;

import com.likelion.commonmodule.exception.jwt.SecurityCustomException;
import com.likelion.commonmodule.exception.jwt.SecurityErrorCode;
import com.likelion.coremodule.user.decoder.OidcJwtDecoder;
import com.likelion.coremodule.user.dto.OidcDecodePayload;
import com.likelion.coremodule.user.dto.OidcPublicKeyListResponse;
import com.likelion.coremodule.user.dto.OidcPublicKeyResponse;
import com.likelion.coremodule.user.infrastructure.KakaoOidcKeyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class KakaoIdTokenDecodeService {
    private final KakaoOidcKeyClient kakaoOidcKeyClient;
    private final String iss;
    private final String clientId;

    public KakaoIdTokenDecodeService(
            KakaoOidcKeyClient kakaoOidcKeyClient,
            @Value("${oauth.kakao.iss}") String iss,
            @Value("${oauth.kakao.client-id}") String clientId
    ) {
        this.kakaoOidcKeyClient = kakaoOidcKeyClient;
        this.iss = iss;
        this.clientId = clientId;
    }

    @Transactional(propagation = Propagation.MANDATORY) // Redis에 접근하므로 Tranasactional
    public OidcDecodePayload getPayloadFromIdToken(final String token) {
        final String kid = getKidFromUnsignedIdToken(token);
        final OidcPublicKeyListResponse kakaoPublicKeyList = kakaoOidcKeyClient.getKakaoOidcOpenKeys();

        final OidcPublicKeyResponse oidcPublicKey =
                kakaoPublicKeyList.keys().stream()
                        .filter(o -> o.kid().equals(kid))
                        .findFirst()
                        .orElseThrow(() -> new SecurityCustomException(SecurityErrorCode.KAKAO_KEY_SERVER_ERROR));

        return OidcJwtDecoder.getOidcTokenBody(token, oidcPublicKey.n(), oidcPublicKey.e());
    }

    private String getKidFromUnsignedIdToken(final String token) {
        return OidcJwtDecoder.getKidFromUnsignedTokenHeader(token, iss, clientId);
    }
}
