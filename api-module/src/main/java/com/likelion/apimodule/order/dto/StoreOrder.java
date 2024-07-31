package com.likelion.apimodule.order.dto;

import com.likelion.coremodule.VisitList.domain.VisitStatus;

public record StoreOrder(
        String storeName,
        Integer price,
        VisitStatus visitStatus
) {
}
