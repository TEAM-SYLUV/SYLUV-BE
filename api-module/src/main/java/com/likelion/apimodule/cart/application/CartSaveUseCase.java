package com.likelion.apimodule.cart.application;

import com.likelion.apimodule.cart.dto.CartSaveReq;
import com.likelion.apimodule.cart.dto.CartUpdateReq;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.cart.domain.Cart;
import com.likelion.coremodule.cart.exception.CartErrorCode;
import com.likelion.coremodule.cart.exception.CartException;
import com.likelion.coremodule.cart.service.CartQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartSaveUseCase {

    private final CartQueryService cartQueryService;
    private final JwtUtil jwtUtil;
    private final UserQueryService userQueryService;
    private final MenuQueryService menuQueryService;

    public Integer findMyCartCount(Long userId, Long menuId) {
        return cartQueryService.findCartByUserIdAndMenuId(userId, menuId);
    }

    public void saveMyCart(String accessToken, CartSaveReq saveReq) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        Menu menu = menuQueryService.findMenuById(saveReq.menuId());

        if (findMyCartCount(user.getUserId(), menu.getId()) >= 1) {
            throw new CartException(CartErrorCode.NO_CART_INFO);
        } else {
            final Cart cart = Cart.builder().user(user).menu(menu).quantity(saveReq.quantity()).build();
            cartQueryService.saveCart(cart);
        }
    }

    public void updateMyCart(String accessToken, List<CartUpdateReq> updateReqs) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        for (CartUpdateReq req : updateReqs) {

            Cart cart = cartQueryService.findCartByUserIdAndCartId(user.getUserId(), req.cartId());
            cart.setCartQuantity(req.quantity());
        }
    }

    public void deleteMyCart(String accessToken, Long cartId) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        cartQueryService.deleteCartByUserIdAndCartId(user.getUserId(), cartId);
    }
}
