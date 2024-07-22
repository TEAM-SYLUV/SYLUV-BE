package com.likelion.coremodule.cart.service;

import com.likelion.coremodule.cart.domain.Cart;
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

    public List<Cart> findCartsByUserId(Long userId) {
        return cartRepository.findCartsByUserUserId(userId);
    }
}
