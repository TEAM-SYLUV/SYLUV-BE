package com.likelion.apimodule.market.dto;

import com.likelion.coremodule.VisitList.domain.VisitStatus;

public record VisitListInfo(Long visitListId,
                            Long storeId,
                            String store,
                            String imageUrl,
                            VisitStatus status) {
}
