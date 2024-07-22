package com.likelion.apimodule.store.dto;

import java.util.List;

public record StoreInfo(Long storeId,
                        String name,
                        Integer reviewCount,
                        String location,
                        String openHours,
                        String closeHours,
                        String contact,
                        String storeImage,
                        List<MenuDetailDTO> menuDetails) {
}
