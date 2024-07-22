package com.likelion.apimodule.store.dto;

import com.likelion.coremodule.store.domain.StoreCategory;

public record StoreResponse(String name,
                            StoreCategory category,
                            String location,
                            String openHours,
                            String image) {
}
