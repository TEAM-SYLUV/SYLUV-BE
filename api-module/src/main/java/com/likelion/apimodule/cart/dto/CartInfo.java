package com.likelion.apimodule.cart.dto;

public record CartInfo(
        Long cartid, // cart
        String storeName, // store
        String menuName,
        String menuImg,
        Integer price, // menu
        Integer quantity // cart
) {
}
