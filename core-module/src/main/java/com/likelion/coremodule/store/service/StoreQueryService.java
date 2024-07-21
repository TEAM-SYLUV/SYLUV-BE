package com.likelion.coremodule.store.service;

import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.exception.StoreErrorCode;
import com.likelion.coremodule.store.exception.StoreException;
import com.likelion.coremodule.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreQueryService {

    private final StoreRepository storeRepository;

    public Store findStoreById(Long storeId) {

        return storeRepository.findById(storeId).orElseThrow(() -> new StoreException(StoreErrorCode.NO_STORE_INFO));
    }

    public List<Store> findAllStore() {

        return storeRepository.findAll();
    }
}
