package com.likelion.apimodule.order.dto;

import com.likelion.coremodule.VisitList.domain.VisitStatus;
import com.likelion.coremodule.store.domain.Store;

public record StoreOrder(
        String storeName,
        Integer price,
        VisitStatus visitStatus,
        Store store
) {

    public Store getStore() {
        return store;
    }
}
