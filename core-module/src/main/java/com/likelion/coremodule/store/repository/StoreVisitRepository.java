package com.likelion.coremodule.store.repository;

import com.likelion.coremodule.store.domain.StoreVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreVisitRepository extends JpaRepository<StoreVisit, Long> {

    StoreVisit findByStoreId(Long storeId);

    List<StoreVisit> findStoreVisitsByUserUserIdAndStoreId(Long userId, Long storeId);

    List<StoreVisit> findStoreVisitsByStoreId(Long storeId);
}
