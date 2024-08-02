package com.likelion.apimodule.order.application;

import com.likelion.apimodule.order.dto.MenuInfo;
import com.likelion.apimodule.order.dto.MenuOrder;
import com.likelion.apimodule.order.dto.OrderDetail;
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
import com.likelion.coremodule.review.service.ReviewQueryService;
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
    private final ReviewQueryService reviewQueryService;

    public Map<LocalDate, List<OrderInfo>> findAllOrdersByDate(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<Order> orders = orderQueryService.findOrderByUserId(user.getUserId());
        Map<LocalDate, List<OrderInfo>> ordersByDate = new HashMap<>();

        for (Order order : orders) {

            List<OrderItem> orderItems = orderQueryService.findOrderItemByOrderId(order.getId());

            List<Menu> menus = new ArrayList<>();
            List<MenuInfo> menuInfos = new ArrayList<>();
            for (OrderItem o : orderItems) {
                Menu menu = menuQueryService.findMenuById(o.getMenu().getId());
                menus.add(menu);

                MenuInfo menuInfo = new MenuInfo(menu.getName(), menu.getImageUrl());
                menuInfos.add(menuInfo);
            }
            Store store = storeQueryService.findStoreById(menus.get(0).getStore().getId());
            Market market = marketQueryService.findMarket(store.getMarket().getId());

            Integer price = 0;
            for (OrderItem o : orderItems) {
                Menu singleMenu = menuQueryService.findMenuById(o.getMenu().getId());
                price += singleMenu.getPrice();
            }

            OrderInfo orderInfo = new OrderInfo(
                    order.getId(),
                    "결제 확인 대기",
                    market.getName(),
                    store.getName(),
                    store.getImageUrl(),
                    menuInfos,
                    price,
                    order.getCreatedAt()
            );

            // 날짜별로 OrderInfo 리스트를 담기
            LocalDate orderDate = order.getCreatedAt().toLocalDate();
            ordersByDate.computeIfAbsent(orderDate, k -> new ArrayList<>()).add(orderInfo);
        }

        return ordersByDate;
    }

    public OrderDetail findMyOrderDetail(String accessToken, Long orderId) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        Order order = orderQueryService.findOrderById(orderId);
        List<OrderItem> items = orderQueryService.findOrderItemByOrderId(orderId);
        Menu menu = menuQueryService.findMenuById(items.get(0).getMenu().getId());
        Store store = storeQueryService.findStoreById(menu.getStore().getId());
//        Market market = marketQueryService.findMarket(store.getMarket().getId());

        List<MenuOrder> menuOrders = new ArrayList<>();
        boolean reviewYn = reviewQueryService.findReviewByOrderAndUser(order.getId(), user.getUserId()) != null;

        Integer price = 0;
        for (OrderItem o : items) {

            Menu singleMenu = menuQueryService.findMenuById(o.getMenu().getId());
            price += singleMenu.getPrice();

            MenuOrder menuOrder = new MenuOrder(
                    singleMenu.getName(),
                    singleMenu.getImageUrl(),
                    o.getQuantity(),
                    singleMenu.getPrice()
            );
            menuOrders.add(menuOrder);
        }

        return new OrderDetail(
                store.getName(),
                store.getImageUrl(),
                order.getCreatedAt(),
                order.getOrderNum(),
                price,
                "토스페이",
                reviewYn,
                menuOrders
        );
    }

}
