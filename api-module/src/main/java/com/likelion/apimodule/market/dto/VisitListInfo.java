package com.likelion.apimodule.market.dto;

import com.likelion.coremodule.VisitList.domain.VisitStatus;

public record VisitListInfo(String store,
                            VisitStatus status) {
}
