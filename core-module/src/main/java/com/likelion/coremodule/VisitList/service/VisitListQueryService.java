package com.likelion.coremodule.VisitList.service;

import com.likelion.coremodule.VisitList.domain.VisitList;
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
}
