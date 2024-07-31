package com.likelion.apimodule.home.application;

import com.likelion.apimodule.home.dto.HomeInfo;
import com.likelion.apimodule.home.dto.HotListHome;
import com.likelion.apimodule.home.dto.MarketFiltered;
import com.likelion.apimodule.home.dto.VisitListHome;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeFindUseCase {

    private final HomeQueryService homeQueryService;
    private final MarketQueryService marketQueryService;
    private final StoreQueryService storeQueryService;
    private final JwtUtil jwtUtil;
    private final UserQueryService userQueryService;

    public List<MarketFiltered> findAllMarkets() {

        return marketQueryService.findAllMarkets().stream()
                .map(market -> new MarketFiltered(
                        market.getId(),          // marketId
                        market.getName()         // marketName
                ))
                .collect(Collectors.toList());
    }

    public HomeInfo findMarketLists(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<Market> marketList = marketQueryService.findAllMarkets();

        List<VisitListHome> visitListHomeList = new ArrayList<>();
        List<HotListHome> hotListHomeList = new ArrayList<>();

        for (Market market : marketList) {
            List<Store> storeList = storeQueryService.findStoresByMarketId(market.getId());
            int totalStoreVisits = 0;

            for (Store store : storeList) {
                List<StoreVisit> storeVisitList = storeQueryService.findStoreVisitsByUserIdAndStoreId(user.getUserId(), store.getId());
                totalStoreVisits += storeVisitList.size();
            }

            VisitListHome visitListHome = new VisitListHome(
                    market.getId(),
                    market.getName(),
                    market.getLocation(),
                    storeList.size(),
                    totalStoreVisits,
                    market.getCreatedAt().toLocalDate()
            );
            visitListHomeList.add(visitListHome);

            MarketQrVisit marketQrVisit = marketQueryService.findMarketVisit(market.getId());
            int qrVisit = (marketQrVisit != null) ? marketQrVisit.getQrVisit() : 0;

            HotListHome hotListHome = new HotListHome(
                    market.getId(),
                    market.getName(),
                    market.getLocation(),
                    qrVisit
            );
            hotListHomeList.add(hotListHome);
        }

        // VisitListHome 리스트를 날짜 기준으로 정렬
        visitListHomeList.sort(Comparator.comparing(VisitListHome::visitDate));
        // HotListHome 리스트를 qrVisit 순으로 정렬
        hotListHomeList.sort(Comparator.comparingInt(HotListHome::totalQrVisit));

        return new HomeInfo(visitListHomeList, hotListHomeList);
    }

    public String findNearestMarket(Integer xloc, Integer yloc) {
        List<Market> marketList = marketQueryService.findAllMarkets();
        String nearestMarketName = null;
        double shortestDistance = Double.MAX_VALUE;

        for (Market market : marketList) {
            double distance = Math.sqrt(Math.pow(market.getXloc() - xloc, 2) + Math.pow(market.getYloc() - yloc, 2));
            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestMarketName = market.getName();
            }
        }
        return nearestMarketName;
    }

}
