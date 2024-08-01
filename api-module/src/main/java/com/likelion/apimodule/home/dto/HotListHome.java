package com.likelion.apimodule.home.dto;

public record HotListHome(
        Long marketId,
        String marketName,
        String marketImage,
        String location,
        Integer totalQrVisit
) {
}
