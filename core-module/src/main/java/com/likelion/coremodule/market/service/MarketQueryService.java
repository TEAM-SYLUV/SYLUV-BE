package com.likelion.coremodule.market.service;

import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.domain.VisitStatus;
import com.likelion.coremodule.VisitList.exception.VisitErrorCode;
import com.likelion.coremodule.VisitList.exception.VisitException;
import com.likelion.coremodule.VisitList.repository.VisitListRepository;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.domain.MarketQrVisit;
import com.likelion.coremodule.market.exception.MarketErrorCode;
import com.likelion.coremodule.market.exception.MarketException;
import com.likelion.coremodule.market.repository.MarketQrVisitRepository;
import com.likelion.coremodule.market.repository.MarketRepository;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketQueryService {

    private final MarketRepository marketRepository;
    private final MarketQrVisitRepository marketQrVisitRepository;
    private final VisitListQueryService visitListQueryService;
    private final UserQueryService userQueryService;
    private final StoreQueryService storeQueryService;
    private final VisitListRepository visitListRepository;

    public List<Market> findAllMarkets() {
        return marketRepository.findAll();
    }

    public Market findMarket(Long id) {
        return marketRepository.findById(id).orElseThrow(() -> new MarketException(MarketErrorCode.NO_MARKET_INFO));
    }

    public List<MarketQrVisit> findMarketVisit(Long marketId) {
        return marketQrVisitRepository.findMarketQrVisitsByMarketId(marketId);
    }

    public Integer findMyMarketVisit(Long marketId, Long userId) {
        return marketQrVisitRepository.countAllByMarketIdAndUserUserId(marketId, userId);
    }

    @Transactional
    public void saveVisitListToPayment(Long storeId, String email) {

        User user = userQueryService.findByEmail(email);
        Store store = storeQueryService.findStoreById(storeId);

        // 현재 날짜 가져오기
        LocalDate today = LocalDate.now();

        // 오늘 동일한 사용자가 동일한 가게를 방문한 기록이 있는지 확인
        if (visitListRepository.findVisitListByUserUserIdAndStoreIdAndVisitedDate(user.getUserId(), storeId, today) != null) {
            VisitList visitList = visitListRepository.findVisitListByUserUserIdAndStoreIdAndVisitedDate(user.getUserId(), storeId, today);
            visitList.updateToPayment();
        } else {
            final VisitList visitList = VisitList.builder()
                    .store(store)
                    .user(user)
                    .visit_status(VisitStatus.PAYMENT)
                    .visitedDate(today)
                    .build();
            visitListRepository.save(visitList);
        }
    }

    @Transactional
    public void saveVisitList(Long storeId, String email) {

        User user = userQueryService.findByEmail(email);
        Store store = storeQueryService.findStoreById(storeId);

        // 현재 날짜 가져오기
        LocalDate today = LocalDate.now();

        // 오늘 동일한 사용자가 동일한 가게를 방문한 기록이 있는지 확인
        if (visitListRepository.findVisitListByUserUserIdAndStoreIdAndVisitedDate(user.getUserId(), storeId, today) != null) {
            throw new VisitException(VisitErrorCode.EXIST_VISIT_LIST_INFO);
        } else {
            final VisitList visitList = VisitList.builder()
                    .store(store)
                    .user(user)
                    .visit_status(VisitStatus.BEFORE)
                    .visitedDate(today)
                    .build();
            visitListRepository.save(visitList);
        }
    }

    public void deleteVisitList(Long id) {
        visitListRepository.deleteById(id);
    }

}
