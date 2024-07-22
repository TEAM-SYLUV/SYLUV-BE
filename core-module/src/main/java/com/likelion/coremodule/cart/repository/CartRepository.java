package com.likelion.coremodule.cart.repository;

import com.likelion.coremodule.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartsByUserUserId(Long userId);

    void deleteCartByIdAndUserUserId(Long cartId, Long userId);
}
