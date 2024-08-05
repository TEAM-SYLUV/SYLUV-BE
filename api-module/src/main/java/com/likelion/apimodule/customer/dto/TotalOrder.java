package com.likelion.apimodule.customer.dto;

import com.likelion.apimodule.order.dto.MenuOrder;
import com.likelion.coremodule.VisitList.domain.VisitStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TotalOrder(
        String pickUpRoute,
        String visitHour,
        String visitMin,
        String userName,
        VisitStatus orderStatus,
        LocalDateTime createdAt,
        Long orderId,
        String orderNum,
        List<MenuOrder> menu,
        Integer totalPrice
) {
}
