package com.likelion.coremodule.market.service;

import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.domain.MarketQrVisit;
import com.likelion.coremodule.market.repository.MarketQrVisitRepository;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeQueryService {

    private final MarketQrVisitRepository marketQrVisitRepository;

    public void updateQrVisit(User user, Market market) {

        MarketQrVisit marketQrVisit = marketQrVisitRepository.findByMarketIdAAndUserUserId(market.getId(), user.getUserId());

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
