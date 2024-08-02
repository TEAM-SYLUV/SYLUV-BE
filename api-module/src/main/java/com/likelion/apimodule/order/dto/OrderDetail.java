package com.likelion.apimodule.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetail(
        String storeName,
        String storeImg,
        LocalDateTime orderDate,
        String orderNum,
        Integer totalPrice,
        String paymentTool,
        Boolean reviewYn,
        List<MenuOrder> menuOrders
) {
}
