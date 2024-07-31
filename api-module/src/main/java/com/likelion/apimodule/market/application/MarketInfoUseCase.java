package com.likelion.apimodule.market.application;

import com.likelion.apimodule.market.dto.MarketInfo;
import com.likelion.apimodule.market.dto.VisitListInfo;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.exception.VisitErrorCode;
import com.likelion.coremodule.VisitList.exception.VisitException;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.service.MarketQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketInfoUseCase {

    private final MarketQueryService marketQueryService;
    private final VisitListQueryService visitListQueryService;
    private final StoreQueryService storeQueryService;
    private final UserQueryService userQueryService;
    private final JwtUtil jwtUtil;

    public MarketInfo findMarketInfo(Long marketId) {

        Market market = marketQueryService.findMarket(marketId);

        return new MarketInfo(
                market.getId(),
                market.getName(),
                market.getDescription(),
                market.getStartHour(),
                market.getCloseHour(),
                market.getLocation(),
                market.getContact(),
                market.getImageUrl());
    }

    public void saveVisitList(Long storeId, String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        marketQueryService.saveVisitList(storeId, email);
    }

    public List<VisitListInfo> findVisitList(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<VisitListInfo> visitListInfos = new ArrayList<>();
        List<VisitList> visitLists = visitListQueryService.findVisitListsByUserId(user.getUserId());

        for (VisitList i : visitLists) {
            Long id = i.getId();
            Store store = storeQueryService.findStoreById(i.getStore().getId());

            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = time.format(formatter);

            VisitListInfo visitListInfo = new VisitListInfo(id, store.getId(), store.getName(), store.getImageUrl(), formattedTime, i.getVisit_status());
            visitListInfos.add(visitListInfo);
        }

        return visitListInfos;
    }

    public void deleteVisitList(Long visitListId) {

        if (visitListQueryService.findVisitListById(visitListId) != null) {
            marketQueryService.deleteVisitList(visitListId);
        } else {
            throw new VisitException(VisitErrorCode.NO_VISIT_LIST_INFO);
        }
    }

}
