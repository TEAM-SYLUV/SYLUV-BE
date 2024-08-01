package com.likelion.apimodule.order.application;

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
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private static final DateTimeFormatter ORDER_NUMBER_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final int ORDER_NUMBER_LENGTH = 4;
    private static final SecureRandom random = new SecureRandom();

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

    public OrderDetail findMyOrderDetail(String accessToken, Long orderId) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        Order order = orderQueryService.findOrderById(orderId);
        List<OrderItem> items = orderQueryService.findOrderItemByOrderId(orderId);
        Menu menu = menuQueryService.findMenuById(items.get(0).getMenu().getId());
        Store store = storeQueryService.findStoreById(menu.getStore().getId());
        Market market = marketQueryService.findMarket(store.getMarket().getId());

        List<MenuOrder> menuOrders = new ArrayList<>();

        Integer price = 0;
        for (OrderItem o : items) {

            Menu singleMenu = menuQueryService.findMenuById(o.getMenu().getId());
            price += singleMenu.getPrice();

            MenuOrder menuOrder = new MenuOrder(
                    singleMenu.getName(),
                    o.getQuantity(),
                    singleMenu.getPrice()
            );
            menuOrders.add(menuOrder);
        }

        return new OrderDetail(
                store.getName(),
                order.getCreatedAt(),
                generateOrderNumber(order.getCreatedAt()),
                price,
                "토스페이",
                menuOrders
        );
    }

    public String generateOrderNumber(LocalDateTime createdAt) {
        String datePart = createdAt.format(ORDER_NUMBER_DATE_FORMAT);
        String orderNumberPart = generateRandomNumber();
        return datePart + orderNumberPart;
    }

    private String generateRandomNumber() {
        int number = random.nextInt(10000);
        return String.format("%04d", number);
    }

}
