package com.likelion.apimodule.cart.dto;

public record CartSaveReq(
        Long menuId,
        Integer quantity
) {
}
