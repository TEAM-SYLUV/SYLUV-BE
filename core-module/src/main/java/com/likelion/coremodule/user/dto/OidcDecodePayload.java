package com.likelion.coremodule.user.dto;

public record OidcDecodePayload(
        String iss,
        String aud,
        String sub,
        String nickname,
        String picture
) {
}
