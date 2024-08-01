package com.likelion.apimodule.order.dto;

import java.time.LocalDateTime;

public record OrderInfo(
        String marketName,
        String storeName,
        Integer totalPrice,
        LocalDateTime createdTime
) {
}
