package com.likelion.coremodule.market.service;

import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.domain.VisitStatus;
import com.likelion.coremodule.VisitList.exception.VisitErrorCode;
import com.likelion.coremodule.VisitList.exception.VisitException;
import com.likelion.coremodule.VisitList.repository.VisitListRepository;
import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.exception.MarketErrorCode;
import com.likelion.coremodule.market.exception.MarketException;
import com.likelion.coremodule.market.repository.MarketRepository;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketQueryService {

    private final MarketRepository marketRepository;
    private final UserQueryService userQueryService;
    private final StoreQueryService storeQueryService;
    private final VisitListRepository visitListRepository;

    public Market findMarket(Long id) {
        return marketRepository.findById(id).orElseThrow(() -> new MarketException(MarketErrorCode.NO_MARKET_INFO));
    }

    public void saveVisitList(Long storeId, String email) {

        User user = userQueryService.findByEmail(email);
        Store store = storeQueryService.findStoreById(storeId);

        if (visitListRepository.findVisitListByUserUserIdAndStoreId(user.getUserId(), storeId) != null) {
            throw new VisitException(VisitErrorCode.EXIST_VISIT_LIST_INFO);
        } else {

            final VisitList visitList = VisitList.builder()
                    .store(store)
                    .user(user)
                    .visit_status(VisitStatus.BEFORE)
                    .build();
            visitListRepository.save(visitList);
        }
    }

    public void deleteVisitList(Long id) {
        visitListRepository.deleteById(id);
    }

}
