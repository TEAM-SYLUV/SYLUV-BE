package com.likelion.apimodule.cart.dto;

public record CartInfo(
        String storeName, // store
        String menuName, // menu
        Integer price, // menu
        Integer quantity // cart
) {
}
