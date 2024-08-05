package com.likelion.coremodule.market.repository;

import com.likelion.coremodule.market.domain.MarketQrVisit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketQrVisitRepository extends JpaRepository<MarketQrVisit, Long> {

    MarketQrVisit findByMarketIdAAndUserUserId(Long marketId, Long userId);

    Integer countAllByMarketIdAndUserUserId(Long marketId, Long userId);
}
