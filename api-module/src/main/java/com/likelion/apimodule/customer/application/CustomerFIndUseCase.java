package com.likelion.apimodule.customer.application;

import com.likelion.apimodule.customer.dto.TotalOrder;
import com.likelion.apimodule.order.dto.MenuOrder;
import com.likelion.coremodule.customer.service.CustomerQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.domain.OrderItem;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerFIndUseCase {

    private final CustomerQueryService customerQueryService;
    private final MenuQueryService menuQueryService;
    private final OrderQueryService orderQueryService;
    private final UserQueryService userQueryService;

    public List<TotalOrder> getTotalOrder(Long storeId) {

        List<Menu> menus = menuQueryService.findMenusByStoreId(storeId);
        List<OrderItem> items = new ArrayList<>();
        for (Menu menu : menus) {
            List<OrderItem> item = orderQueryService.findOrderItemsByMenuId(menu.getId());
            items.addAll(item);
        }

        Map<Long, List<OrderItem>> groupedItems = new HashMap<>();
        for (OrderItem item : items) {
            groupedItems.computeIfAbsent(item.getOrder().getId(), k -> new ArrayList<>()).add(item);
        }

        List<TotalOrder> totalOrders = new ArrayList<>();

        for (Map.Entry<Long, List<OrderItem>> entry : groupedItems.entrySet()) {

            List<OrderItem> orderItems = entry.getValue();
            Order order = orderQueryService.findOrderById(orderItems.get(0).getOrder().getId());
            User user = userQueryService.findById(order.getUser().getUserId());

            List<MenuOrder> menuOrders = orderItems.stream()
                    .map(item -> {

                        Menu menu = menuQueryService.findMenuById(item.getMenu().getId());

                        return new MenuOrder(
                                menu.getName(),
                                menu.getImageUrl(),
                                item.getQuantity(),
                                menu.getPrice() * item.getQuantity()
                        );
                    })
                    .collect(Collectors.toList());

            Integer totalPrice = menuOrders.stream()
                    .mapToInt(MenuOrder::totalPrice)
                    .sum();

            TotalOrder totalOrder = new TotalOrder(
                    order.getPickUpRoute(),
                    order.getVisitHour().toString(),
                    order.getVisitMin().toString(),
                    user.getName(),
                    order.getCreatedAt(),
                    order.getOrderNum(),
                    menuOrders,
                    totalPrice
            );

            totalOrders.add(totalOrder);
        }

        return totalOrders;
    }
}
