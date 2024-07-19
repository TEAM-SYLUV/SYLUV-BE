package com.likelion.apimodule.market.dto;

public record MarketInfo(String name,
                         String description,
                         String startHour,
                         String closeHour,
                         String contact) {
}
