package com.likelion.apimodule.order.application;

import com.likelion.apimodule.cart.dto.CartInfo;
import com.likelion.apimodule.order.dto.OrderInfo;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFindUseCase {

    private final JwtUtil jwtUtil;
    private final UserQueryService userQueryService;

    public List<OrderInfo> findAllCarts(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);


    }

}
