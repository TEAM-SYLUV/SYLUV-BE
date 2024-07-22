package com.likelion.apimodule.cart.application;

import com.likelion.apimodule.cart.dto.CartSaveReq;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.cart.domain.Cart;
import com.likelion.coremodule.cart.service.CartQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartSaveUseCase {

    private final CartQueryService cartQueryService;
    private final JwtUtil jwtUtil;
    private final UserQueryService userQueryService;
    private final MenuQueryService menuQueryService;

    public void saveMyCart(String accessToken, CartSaveReq saveReq) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        Menu menu = menuQueryService.findMenuById(saveReq.menuId());

        final Cart cart = Cart.builder().user(user).menu(menu).quantity(saveReq.quantity()).build();
        cartQueryService.saveCart(cart);
    }
}
