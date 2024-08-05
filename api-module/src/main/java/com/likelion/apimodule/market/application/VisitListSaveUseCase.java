package com.likelion.apimodule.market.application;

import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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
