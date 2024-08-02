package com.likelion.apimodule.order.dto;

public record MenuOrder(
        String menuName,
        String menuImg,
        Integer quantity,
        Integer totalPrice
) {
}
