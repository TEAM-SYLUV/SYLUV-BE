package com.likelion.apimodule.order.application;

import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderDeleteUseCase {

    private final OrderQueryService orderQueryService;
    private final UserQueryService userQueryService;
    private final JwtUtil jwtUtil;

    public void deleteMyOrder(String accessToken, Long orderId) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        orderQueryService.deleteMyOrder(orderId, user.getUserId());
    }

}