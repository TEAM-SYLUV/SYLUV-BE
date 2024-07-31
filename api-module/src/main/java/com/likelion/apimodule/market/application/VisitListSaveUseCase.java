package com.likelion.apimodule.market.application;

import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitListSaveUseCase {

    private final VisitListQueryService visitListQueryService;

    public void updateToPrepared(String accessToken, Long visitListId) {

        VisitList visitList = visitListQueryService.findVisitListById(visitListId);
        visitList.updateToPrepared();
    }

    public void updateToVisited(String accessToken, Long visitListId) {

        VisitList visitList = visitListQueryService.findVisitListById(visitListId);
        visitList.updateToVisited();
    }
}
