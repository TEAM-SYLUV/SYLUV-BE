package com.likelion.coremodule.VisitList.service;

import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.exception.VisitErrorCode;
import com.likelion.coremodule.VisitList.exception.VisitException;
import com.likelion.coremodule.VisitList.repository.VisitListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitListQueryService {

    private final VisitListRepository visitListRepository;

    public List<VisitList> findAllVisitList() {
        return visitListRepository.findAll();
    }

    public VisitList findVisitListById(Long id) {
        return visitListRepository.findById(id).orElseThrow(() -> new VisitException(VisitErrorCode.NO_VISIT_LIST_INFO));
    }

    public void saveVisitList(VisitList visitList) {
        visitListRepository.save(visitList);
    }

    public List<VisitList> findVisitListsByUserId(Long userId) {
        return visitListRepository.findVisitListsByUserUserId(userId);
    }

    public Integer countVisitListByStoreAndUser(Long storeId, Long userId) {
        return visitListRepository.countVisitListsByStoreIdAndUserUserId(storeId, userId);
    }

    public VisitList findVisitListByStoreIdAndUserId(Long storeId, Long userId) {
        return visitListRepository.findVisitListByStoreIdAndUserUserId(storeId, userId);
    }
}
