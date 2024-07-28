package com.likelion.coremodule.store.repository;

import com.likelion.coremodule.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findStoresByMarketId(Long marketId);
}
