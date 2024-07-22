package com.likelion.apimodule.market.dto;

public record MarketInfo(Long marketId,
                         String name,
                         String description,
                         String startHour,
                         String closeHour,
                         String contact,
                         String image) {
}
