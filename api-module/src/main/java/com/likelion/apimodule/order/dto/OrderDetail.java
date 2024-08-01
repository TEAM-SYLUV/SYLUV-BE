package com.likelion.apimodule.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetail(
        String storeName,
        LocalDateTime orderDate,
        String orderNum,
        Integer totalPrice,
        String paymentTool,
        List<MenuOrder> menuOrders
) {
}
