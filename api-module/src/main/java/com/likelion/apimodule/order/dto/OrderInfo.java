package com.likelion.apimodule.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderInfo(
        Long orderId,
        String paymentStatus,
        String marketName,
        String storeName,
        String imageUrl,
        List<MenuInfo> menuInfos,
        Integer totalPrice,
        LocalDateTime createdTime,
        Boolean reviewYn
) {
}
