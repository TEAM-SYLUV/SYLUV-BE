package com.likelion.apimodule.market.application;

import com.likelion.apimodule.market.dto.MarketInfo;
import com.likelion.apimodule.market.dto.VisitListInfo;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.domain.VisitStatus;
import com.likelion.coremodule.VisitList.exception.VisitErrorCode;
import com.likelion.coremodule.VisitList.exception.VisitException;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.service.MarketQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketInfoUseCase {

    private final MarketQueryService marketQueryService;
    private final VisitListQueryService visitListQueryService;
    private final StoreQueryService storeQueryService;
    private final UserQueryService userQueryService;
    private final JwtUtil jwtUtil;

    public MarketInfo findMarketInfo(Long marketId) {

        Market market = marketQueryService.findMarket(marketId);

        return new MarketInfo(
                market.getId(),
                market.getName(),
                market.getDescription(),
                market.getStartHour(),
                market.getCloseHour(),
                market.getLocation(),
                market.getContact(),
                market.getImageUrl());
    }

    public void saveVisitList(Long storeId, String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        marketQueryService.saveVisitList(storeId, email);
    }

    public Map<LocalDate, List<VisitListInfo>> findVisitList(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<VisitList> visitLists = visitListQueryService.findVisitListsByUserId(user.getUserId());

        Map<LocalDate, List<VisitList>> groupedByDate = visitLists.stream()
                .collect(Collectors.groupingBy(vl -> vl.getCreatedAt().toLocalDate()));

        Map<LocalDate, List<VisitListInfo>> visitListInfosByDate = new LinkedHashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Map.Entry<LocalDate, List<VisitList>> entry : groupedByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<VisitList> visitListsForDate = entry.getValue();

            List<VisitList> sortedVisitListsForDate = visitListsForDate.stream()
                    .sorted(Comparator.comparing(VisitList::getCreatedAt))
                    .toList();

            List<VisitListInfo> visitListInfos = sortedVisitListsForDate.stream()
                    .map(visitList -> {
                        Long id = visitList.getId();
                        Store store = storeQueryService.findStoreById(visitList.getStore().getId());

                        LocalDateTime time = visitList.getCreatedAt();
                        String formattedTime = time.format(formatter);

                        return new VisitListInfo(
                                id,
                                store.getId(),
                                store.getName(),
                                store.getImageUrl(),
                                formattedTime,
                                visitList.getVisit_status()
                        );
                    })
                    .collect(Collectors.toList());

            visitListInfosByDate.put(date, visitListInfos);
        }

        return visitListInfosByDate;
    }

    public Map<LocalDate, List<VisitListInfo>> findTodayVisitList (String accessToken) {
        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<VisitList> visitLists = visitListQueryService.findVisitListsByUserId(user.getUserId());

        // 그룹화된 방문 리스트를 날짜별로 나누기
        Map<LocalDate, List<VisitList>> groupedByDate = visitLists.stream()
                .collect(Collectors.groupingBy(vl -> vl.getCreatedAt().toLocalDate()));

        // 날짜별 VisitListInfo 리스트를 저장할 Map
        Map<LocalDate, List<VisitListInfo>> visitListInfosByDate = new LinkedHashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Map.Entry<LocalDate, List<VisitList>> entry : groupedByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<VisitList> visitListsForDate = entry.getValue();

            // LocalDateTime을 기준으로 정렬
            List<VisitList> sortedVisitListsForDate = visitListsForDate.stream()
                    .sorted(Comparator.comparing(VisitList::getCreatedAt))
                    .toList();

            // 날짜별 VisitListInfo 리스트 생성
            List<VisitListInfo> visitListInfos = sortedVisitListsForDate.stream()
                    .map(visitList -> {
                        Long id = visitList.getId();
                        Store store = storeQueryService.findStoreById(visitList.getStore().getId());

                        LocalDateTime time = visitList.getCreatedAt();
                        String formattedTime = time.format(formatter);

                        return new VisitListInfo(
                                id,
                                store.getId(),
                                store.getName(),
                                store.getImageUrl(),
                                formattedTime,
                                visitList.getVisit_status()
                        );
                    })
                    .collect(Collectors.toList());

            visitListInfosByDate.put(date, visitListInfos);
        }

        return visitListInfosByDate;
    }

    public void deleteVisitList(Long visitListId) {

        if (visitListQueryService.findVisitListById(visitListId) != null) {
            marketQueryService.deleteVisitList(visitListId);
        } else {
            throw new VisitException(VisitErrorCode.NO_VISIT_LIST_INFO);
        }
    }

}
