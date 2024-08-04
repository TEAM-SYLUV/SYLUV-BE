package com.likelion.apimodule.customer.application;

import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerSaveUseCase {

    private final VisitListQueryService visitListQueryService;
    private final OrderQueryService orderQueryService;
    private final UserQueryService userQueryService;

    public void changeToPreparing(Long storeId, Long orderId) {

        Order order = orderQueryService.findOrderById(orderId);
        User user = userQueryService.findById(order.getUser().getUserId());

        VisitList visitList = visitListQueryService.findVisitListByStoreIdAndUserId(storeId, user.getUserId());
        visitList.updateToPreparing();
    }

    public void changeToPrepared(Long storeId, Long orderId) {

        Order order = orderQueryService.findOrderById(orderId);
        User user = userQueryService.findById(order.getUser().getUserId());

        VisitList visitList = visitListQueryService.findVisitListByStoreIdAndUserId(storeId, user.getUserId());
        visitList.updateToPrepared();
    }
}
