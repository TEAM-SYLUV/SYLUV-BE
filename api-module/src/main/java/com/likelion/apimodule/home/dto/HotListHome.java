package com.likelion.apimodule.home.dto;

public record HotListHome(
        Long marketId,
        String marketName,
        String location,
        Integer totalQrVisit
) {
}
