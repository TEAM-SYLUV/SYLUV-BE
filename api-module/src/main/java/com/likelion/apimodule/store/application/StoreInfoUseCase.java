package com.likelion.apimodule.store.application;

import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.domain.StoreCategory;
import com.likelion.coremodule.store.exception.StoreErrorCode;
import com.likelion.coremodule.store.exception.StoreException;
import com.likelion.coremodule.store.service.StoreQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreInfoUseCase {

    private final StoreQueryService storeQueryService;

    public List<Store> findStoreByFilter(String search, String category) {

        List<Store> storeList = storeQueryService.findAllStore();

        if ((search == null || search.isEmpty()) && (category == null || category.isEmpty())) {
            return storeList;
        }

        StoreCategory storeCategory = null;
        if (category != null && !category.isEmpty()) {
            try {
                storeCategory = StoreCategory.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new StoreException(StoreErrorCode.NO_STORE_CATEGORY);
            }
        }

        final StoreCategory finalStoreCategory = storeCategory;

        return storeList.stream()
                .filter(store -> (search == null || search.isEmpty() || store.getName().contains(search)))
                .filter(store -> (finalStoreCategory == null || store.getCategory() == finalStoreCategory))
                .toList();
    }

}
