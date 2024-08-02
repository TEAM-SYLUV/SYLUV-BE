package com.likelion.apimodule.order.dto;

import java.time.LocalDateTime;

public record OrderInfo(
        Long orderId,
        String paymentStatus,
        String marketName,
        String storeName,
        Integer totalPrice,
        LocalDateTime createdTime
) {
}
