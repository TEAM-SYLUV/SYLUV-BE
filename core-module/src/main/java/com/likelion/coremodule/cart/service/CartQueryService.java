package com.likelion.coremodule.cart.service;

import com.likelion.coremodule.cart.domain.Cart;
import com.likelion.coremodule.cart.exception.CartErrorCode;
import com.likelion.coremodule.cart.exception.CartException;
import com.likelion.coremodule.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartQueryService {

    private final CartRepository cartRepository;

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public Cart findCartByMenuId(Long menuId) {
        return cartRepository.findCartByMenuId(menuId);
    }

    public Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartException(CartErrorCode.NO_CART_INFO));
    }

    public List<Cart> findCartsByUserId(Long userId) {
        return cartRepository.findCartsByUserUserId(userId);
    }

    public Cart findCartByUserIdAndMenuId(Long userId, Long menuId) {
        return cartRepository.findCartByUserUserIdAndMenuId(userId, menuId);
    }

    public int countCartByUserIdAndMenuId(Long userId, Long menuId) {
        return cartRepository.countCartsByUserUserIdAndMenuId(userId, menuId);
    }

    public Cart findCartByUserIdAndCartId(Long userId, Long cartId) {
        return cartRepository.findCartByUserUserIdAndId(userId, cartId);
    }

    public void deleteCartByUserIdAndCartId(Long userId, Long cartId) {

        cartRepository.deleteCartByIdAndUserUserId(cartId, userId);
    }
}
