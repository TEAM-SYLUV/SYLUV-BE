package com.likelion.coremodule.market.service;

import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.domain.MarketQrVisit;
import com.likelion.coremodule.market.repository.MarketQrVisitRepository;
import com.likelion.coremodule.market.repository.MarketRepository;
import com.likelion.coremodule.store.repository.StoreRepository;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeQueryService {

    private final MarketRepository marketRepository;
    private final MarketQrVisitRepository marketQrVisitRepository;
    private final StoreRepository storeRepository;

    public void updateQrVisit(User user, Market market) {

        MarketQrVisit marketQrVisit = marketQrVisitRepository.findByMarketId(market.getId());

        if (marketQrVisit != null) {
            marketQrVisit.updateQrVisit();
        } else {
            marketQrVisit = MarketQrVisit.builder()
                    .qrVisit(1)
                    .market(market)
                    .user(user)
                    .build();
            marketQrVisitRepository.save(marketQrVisit);
        }
    }

}
