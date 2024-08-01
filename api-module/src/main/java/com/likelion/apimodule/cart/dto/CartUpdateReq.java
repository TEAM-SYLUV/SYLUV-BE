package com.likelion.apimodule.cart.dto;

public record CartUpdateReq(
        Long cartId,
        Integer quantity
) {
}
