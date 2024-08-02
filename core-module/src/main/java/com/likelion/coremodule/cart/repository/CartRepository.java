package com.likelion.coremodule.cart.repository;

import com.likelion.coremodule.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartsByUserUserId(Long userId);

    Cart findCartByUserUserIdAndMenuId(Long userId, Long menuId);

    int countCartsByUserUserIdAndMenuId(Long userId, Long menuId);

    Cart findCartByUserUserIdAndId(Long userId, Long cartId);

    void deleteCartByIdAndUserUserId(Long cartId, Long userId);
}
