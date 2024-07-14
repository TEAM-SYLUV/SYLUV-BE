package com.likelion.coremodule.user.dto;

import java.util.List;

public record OidcPublicKeyListResponse(
        List<OidcPublicKeyResponse> keys
) {
}
