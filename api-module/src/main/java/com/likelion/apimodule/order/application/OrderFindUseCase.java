package com.likelion.apimodule.order.application;

import com.likelion.apimodule.order.dto.OrderInfo;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.VisitList.repository.VisitListRepository;
import com.likelion.coremodule.market.domain.Market;
import com.likelion.coremodule.market.service.MarketQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.domain.OrderItem;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<LocalDate, List<OrderInfo>> findAllOrdersByDate(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<Order> orders = orderQueryService.findOrderByUserId(user.getUserId());
        Map<LocalDate, List<OrderInfo>> ordersByDate = new HashMap<>();

        for (Order order : orders) {

            List<OrderItem> orderItems = orderQueryService.findOrderItemByOrderId(order.getId());
            Menu menu = menuQueryService.findMenuById(orderItems.get(0).getMenu().getId());
            Store store = storeQueryService.findStoreById(menu.getStore().getId());
            Market market = marketQueryService.findMarket(store.getMarket().getId());

            Integer price = 0;
            for (OrderItem o : orderItems) {
                Menu singleMenu = menuQueryService.findMenuById(o.getMenu().getId());
                price += singleMenu.getPrice();
            }

            OrderInfo orderInfo = new OrderInfo(
                    market.getName(),
                    store.getName(),
                    price,
                    order.getCreatedAt()
            );

            // 날짜별로 OrderInfo 리스트를 담기
            LocalDate orderDate = order.getCreatedAt().toLocalDate();
            ordersByDate.computeIfAbsent(orderDate, k -> new ArrayList<>()).add(orderInfo);
        }

        return ordersByDate;
    }

//    public OrderInfo findMyOrderDetail(String accessToken) {
//
//        String email = jwtUtil.getEmail(accessToken);
//        User user = userQueryService.findByEmail(email);
//    }

}
