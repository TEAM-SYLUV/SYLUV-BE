package com.likelion.apimodule.market.dto;

import com.likelion.coremodule.VisitList.domain.VisitStatus;
import com.likelion.coremodule.store.domain.StoreCategory;

public record VisitListInfo(Long visitListId,
                            Long storeId,
                            String store,
                            StoreCategory category,
                            String imageUrl,
                            String visitedTime,
                            VisitStatus status) {
}
