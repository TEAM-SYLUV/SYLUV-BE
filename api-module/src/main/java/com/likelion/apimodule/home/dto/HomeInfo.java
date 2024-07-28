package com.likelion.apimodule.home.dto;

import java.util.List;

public record HomeInfo(
        List<VisitListHome> visitListHomeList,
        List<HotListHome> hotListHomeList
) {
}
