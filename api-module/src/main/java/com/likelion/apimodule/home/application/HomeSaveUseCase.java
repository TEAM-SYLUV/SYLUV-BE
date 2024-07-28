package com.likelion.apimodule.home.application;

import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.domain.MarketQrVisit;
import com.likelion.coremodule.market.service.HomeQueryService;
import com.likelion.coremodule.market.service.MarketQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.domain.StoreVisit;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeSaveUseCase {

    private final HomeQueryService homeQueryService;
    private final UserQueryService userQueryService;
    private final StoreQueryService storeQueryService;
    private final MarketQueryService marketQueryService;
    private final JwtUtil jwtUtil;

    public void updateQrVisit(String accessToken, Long storeId) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        Store store = storeQueryService.findStoreById(storeId);
        Market market = marketQueryService.findMarket(store.getMarket().getId());

        // 방문여부 업데이트
        storeQueryService.saveVisitYn(store, user);
        // qr 조회수 증가
        homeQueryService.updateQrVisit(user, market);
    }
}
