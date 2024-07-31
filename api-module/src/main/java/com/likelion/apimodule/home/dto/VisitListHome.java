package com.likelion.apimodule.home.dto;

import java.time.LocalDate;

public record VisitListHome(
        Long marketId,
        String marketName,
        String location,
        Integer visitedStore,
        Integer totalStore,
        LocalDate visitDate
) {
}
