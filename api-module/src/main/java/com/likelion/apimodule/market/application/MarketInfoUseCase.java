package com.likelion.apimodule.market.application;

import com.likelion.apimodule.market.dto.MarketInfo;
import com.likelion.apimodule.market.dto.VisitListInfo;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.service.MarketQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketInfoUseCase {

    private final MarketQueryService marketQueryService;
    private final VisitListQueryService visitListQueryService;
    private final StoreQueryService storeQueryService;
    private final JwtUtil jwtUtil;

    public MarketInfo findMarketInfo() {

        Market market = marketQueryService.findMarket(1L);

        return new MarketInfo(
                market.getId(),
                market.getName(),
                market.getDescription(),
                market.getStartHour(),
                market.getCloseHour(),
                market.getContact(),
                market.getImageUrl());
    }

    public void saveVisitList(Long storeId, String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        marketQueryService.saveVisitList(storeId, email);
    }

    public List<VisitListInfo> findVisitList() {

        List<VisitListInfo> visitListInfos = new ArrayList<>();
        List<VisitList> visitLists = visitListQueryService.findAllVisitList();

        for (VisitList i : visitLists) {
            Long id = i.getId();
            Store store = storeQueryService.findStoreById(id);

            VisitListInfo visitListInfo = new VisitListInfo(id, store.getId(), store.getName(), i.getVisit_status());
            visitListInfos.add(visitListInfo);
        }

        return visitListInfos;
    }

    public void deleteVisitList(Long visitListId) {

        if (visitListQueryService.findVisitListById(visitListId) == null)
            marketQueryService.deleteVisitList(visitListId);
    }

}
