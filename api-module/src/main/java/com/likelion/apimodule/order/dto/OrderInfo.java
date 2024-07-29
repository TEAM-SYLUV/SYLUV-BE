package com.likelion.apimodule.order.dto;

import java.util.List;

public record OrderInfo(
        String marketName,
        List<StoreOrder> orderList,
        Integer totalPrice,
        String date

) {
}
