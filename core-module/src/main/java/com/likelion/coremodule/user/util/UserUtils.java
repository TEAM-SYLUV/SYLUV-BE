package com.likelion.coremodule.user.util;

import com.likelion.commonmodule.security.util.SecurityUtils;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtils {

    private final UserQueryService userQueryService;

    public User getAccessMember(){
        final String email = SecurityUtils.getAuthenticationPrincipal();
        return userQueryService.findByEmail(email);
    }

    public static String getEmailFromAccessUser(){
        return SecurityUtils.getAuthenticationPrincipal();
    }
}
