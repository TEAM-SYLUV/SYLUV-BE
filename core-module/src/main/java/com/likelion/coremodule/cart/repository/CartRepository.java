package com.likelion.coremodule.cart.repository;

import com.likelion.coremodule.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
