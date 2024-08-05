package com.likelion.coremodule.VisitList.repository;

import com.likelion.coremodule.VisitList.domain.VisitList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitListRepository extends JpaRepository<VisitList, Long> {

    VisitList findVisitListByUserUserIdAndStoreIdAndVisitedDate(Long userId, Long storeId, LocalDate today);

    List<VisitList> findVisitListsByUserUserId(Long userId);

    VisitList findVisitListByStoreIdAndUserUserId(Long storeId, Long userId);

    Integer countVisitListsByStoreIdAndUserUserId(Long storeId, Long userId);
}
