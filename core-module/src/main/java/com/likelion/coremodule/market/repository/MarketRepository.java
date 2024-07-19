package com.likelion.coremodule.market.repository;

import com.likelion.coremodule.market.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
