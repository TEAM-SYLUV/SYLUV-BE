package com.likelion.apimodule.order.dto;

public record MenuOrder(
        String menuName,
        Integer quantity,
        Integer totalPrice
) {
}
