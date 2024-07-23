package com.likelion.apimodule.cart.application;

import com.likelion.apimodule.cart.dto.CartInfo;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.cart.domain.Cart;
import com.likelion.coremodule.cart.service.CartQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartFindUseCase {

    private final CartQueryService cartQueryService;
    private final UserQueryService userQueryService;
    private final StoreQueryService storeQueryService;
    private final MenuQueryService menuQueryService;
    private final JwtUtil jwtUtil;

    public List<CartInfo> findAllCarts(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<Cart> cartList = cartQueryService.findCartsByUserId(user.getUserId());
        List<CartInfo> cartInfoList = new ArrayList<>();

        for (Cart cart : cartList) {

            Menu menu = menuQueryService.findMenuById(cart.getMenu().getId());
            Store store = storeQueryService.findStoreById(menu.getStore().getId());

            CartInfo cartInfo = new CartInfo(
                    store.getName(),
                    menu.getName(),
                    menu.getPrice(),
                    cart.getQuantity()
            );
            cartInfoList.add(cartInfo);
        }

        return cartInfoList;
    }
}
