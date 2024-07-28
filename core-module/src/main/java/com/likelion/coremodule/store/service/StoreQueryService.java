package com.likelion.coremodule.store.service;

import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.domain.StoreVisit;
import com.likelion.coremodule.store.exception.StoreErrorCode;
import com.likelion.coremodule.store.exception.StoreException;
import com.likelion.coremodule.store.repository.StoreRepository;
import com.likelion.coremodule.store.repository.StoreVisitRepository;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreQueryService {

    private final StoreRepository storeRepository;
    private final StoreVisitRepository storeVisitRepository;

    public Store findStoreById(Long storeId) {

        return storeRepository.findById(storeId).orElseThrow(() -> new StoreException(StoreErrorCode.NO_STORE_INFO));
    }

    public List<StoreVisit> findStoreVisitsByUserIdAndStoreId(Long userId, Long storeId) {
        return storeVisitRepository.findStoreVisitsByUserUserIdAndStoreId(userId, storeId);
    }

    public StoreVisit findStoreVisitByStoreId(Long storeId) {
        return storeVisitRepository.findByStoreId(storeId);
    }

    public List<Store> findAllStore() {
        return storeRepository.findAll();
    }

    public List<Store> findStoresByMarketId(Long marketId) {
        return storeRepository.findStoresByMarketId(marketId);
    }

    public List<StoreVisit> findStoreVisitsByStoreId(Long storeId) {
        return storeVisitRepository.findStoreVisitsByStoreId(storeId);
    }

    public void saveVisitYn(Store store, User user) {
        final StoreVisit storeVisit = StoreVisit.builder()
                .store(store).user(user).visitYn(true).build();
        storeVisitRepository.save(storeVisit);
    }
}
