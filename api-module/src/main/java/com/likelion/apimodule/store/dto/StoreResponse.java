package com.likelion.apimodule.store.dto;

import com.likelion.coremodule.store.domain.StoreCategory;

public record StoreResponse(Long storeId,
                            Long marketId,
                            String name,
                            StoreCategory category,
                            String description,
                            String location,
                            String openHours,
                            String image) {
}
