package com.likelion.apimodule.order.application;

import com.likelion.apimodule.order.dto.OrderInfo;
import com.likelion.apimodule.order.dto.StoreOrder;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.repository.VisitListRepository;
import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.service.MarketQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFindUseCase {

    private final JwtUtil jwtUtil;
    private final UserQueryService userQueryService;
    private final OrderQueryService orderQueryService;
    private final StoreQueryService storeQueryService;
    private final MenuQueryService menuQueryService;
    private final MarketQueryService marketQueryService;
    private final VisitListRepository visitListRepository;

    public List<OrderInfo> findAllOrders(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<Order> orders = orderQueryService.findOrderByUserId(user.getUserId());
        List<OrderInfo> orderInfos = new ArrayList<>();

        for (Order order : orders) {
            List<StoreOrder> storeOrderList = new ArrayList<>();
            int totalPrice = 0;

            List<VisitList> visitLists = visitListRepository.findVisitListsByUserUserId(user.getUserId());

            for (VisitList visit : visitLists) {

                Store store = storeQueryService.findStoreById(visit.getStore().getId());
                List<Menu> menus = menuQueryService.findMenusByStoreId(store.getId());

                int storeTotalPrice = menus.stream().mapToInt(Menu::getPrice).sum();
                StoreOrder storeOrder = new StoreOrder(
                        store.getName(),
                        storeTotalPrice,
                        visit.getVisit_status(),
                        store
                );
                storeOrderList.add(storeOrder);

                // 총 가격을 계산합니다.
                totalPrice += storeTotalPrice;
            }

            if (!storeOrderList.isEmpty()) {
                Store store = storeOrderList.get(0).getStore();
                Market market = store.getMarket();

                OrderInfo orderInfo = new OrderInfo(
                        market.getName(),
                        storeOrderList,
                        totalPrice,
                        order.getCreatedAt().toString()
                );
                orderInfos.add(orderInfo);
            }
        }

        return orderInfos;
    }

//    public OrderInfo findMyOrderDetail(String accessToken) {
//
//        String email = jwtUtil.getEmail(accessToken);
//        User user = userQueryService.findByEmail(email);
//    }

}
