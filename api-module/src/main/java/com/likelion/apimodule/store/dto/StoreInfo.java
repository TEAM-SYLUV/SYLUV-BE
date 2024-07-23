package com.likelion.apimodule.store.dto;

import com.likelion.coremodule.store.domain.StoreCategory;

import java.util.List;

public record StoreInfo(Long storeId,
                        String name,
                        Integer reviewCount,
                        Double ratingAvg,
                        String location,
                        String openHours,
                        String closeHours,
                        StoreCategory category,
                        String contact,
                        String storeImage,
                        List<MenuDetailDTO> menuDetails) {
}
