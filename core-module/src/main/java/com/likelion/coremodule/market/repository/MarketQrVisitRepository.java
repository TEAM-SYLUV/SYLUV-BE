package com.likelion.coremodule.market.repository;

import com.likelion.coremodule.market.domain.MarketQrVisit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketQrVisitRepository extends JpaRepository<MarketQrVisit, Long> {

    MarketQrVisit findByMarketId(Long marketId);

    Integer countAllByMarketIdAndUserUserId(Long marketId, Long userId);
}
