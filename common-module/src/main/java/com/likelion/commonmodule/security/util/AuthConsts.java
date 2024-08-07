package com.likelion.commonmodule.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConsts {
    public static final String AUTHENTICATION_TYPE= "Bearer";
    public static final String AUTHORIZATION = "Authorization";
    public static final String EMPTY_HEADER = null;
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String ACCESS_TOKEN_HEADER = "AccessToken";
    public static final String AUTHENTICATION_TYPE_PREFIX = AUTHENTICATION_TYPE+" ";
}
