package com.likelion.apimodule.market.dto;

public record MarketInfo(Long marketId,
                         String name,
                         String description,
                         String startHour,
                         String closeHour,
                         String location,
                         String contact,
                         String image) {
}
