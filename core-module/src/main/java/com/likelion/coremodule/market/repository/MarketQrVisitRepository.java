package com.likelion.coremodule.market.repository;

import com.likelion.coremodule.market.domain.MarketQrVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketQrVisitRepository extends JpaRepository<MarketQrVisit, Long> {

    MarketQrVisit findByMarketIdAndUserUserId(Long marketId, Long userId);

    List<MarketQrVisit> findMarketQrVisitsByMarketId(Long marketId);

    Integer countAllByMarketIdAndUserUserId(Long marketId, Long userId);
}
