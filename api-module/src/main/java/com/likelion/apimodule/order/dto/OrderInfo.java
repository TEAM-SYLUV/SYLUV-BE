package com.likelion.apimodule.order.dto;

import java.util.List;

public record OrderInfo(
        String marketName, // order -> visitlist -> store -> market
        List<StoreOrder> orderList, // store
        Integer totalPrice, // 계산
        String date // order

) {
}
