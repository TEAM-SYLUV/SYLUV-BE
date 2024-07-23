package com.likelion.coremodule.VisitList.repository;

import com.likelion.coremodule.VisitList.domain.VisitList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitListRepository extends JpaRepository<VisitList, Long> {

    VisitList findVisitListByUserUserIdAndStoreId(Long userId, Long storeId);
}
