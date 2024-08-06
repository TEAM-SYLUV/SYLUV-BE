package com.likelion.apimodule.customer.application;

import com.likelion.apimodule.customer.dto.TotalOrder;
import com.likelion.apimodule.order.dto.MenuOrder;
import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
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
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerFIndUseCase {

    private final MenuQueryService menuQueryService;
    private final OrderQueryService orderQueryService;
    private final UserQueryService userQueryService;
    private final VisitListQueryService visitListQueryService;

    public List<TotalOrder> getTotalOrder(Long storeId) {

        List<Menu> menus = menuQueryService.findMenusByStoreId(storeId);
        if (menus == null || menus.isEmpty()) {
            return Collections.emptyList();
        }

        List<OrderItem> items = new ArrayList<>();
        for (Menu menu : menus) {
            List<OrderItem> item = orderQueryService.findOrderItemsByMenuId(menu.getId());
            if (item != null) {
                items.addAll(item);
            }
        }

        List<Order> orders = new ArrayList<>();
        for (OrderItem item : items) {
            Order order = orderQueryService.findOrderById(item.getOrder().getId());
            orders.add(order);
        }

        List<TotalOrder> totalOrders = new ArrayList<>();

        for (Order order : orders) {

            List<OrderItem> orderItems = orderQueryService.findOrderItemByOrderId(order.getId());

            User user = userQueryService.findById(order.getUser().getUserId());
            VisitList visitList = visitListQueryService.findVisitListByStoreIdAndUserId(storeId, user.getUserId());
            if (visitList == null) {
                continue;
            }

            List<Menu> menuList = new ArrayList<>();
            List<MenuOrder> menuOrders = new ArrayList<>();
            int price = 0;

            for (OrderItem item : orderItems) {
                Menu menu = menuQueryService.findMenuById(item.getMenu().getId());
                menuList.add(menu);

                price += menu.getPrice() * item.getQuantity();

                MenuOrder menuOrder = new MenuOrder(
                        menu.getName(),
                        menu.getImageUrl(),
                        item.getQuantity(),
                        menu.getPrice()
                );
                menuOrders.add(menuOrder);
            }

            TotalOrder totalOrder = new TotalOrder(
                    order.getPickUpRoute(),
                    order.getVisitHour().toString(),
                    order.getVisitMin().toString(),
                    user.getName(),
                    visitList.getVisit_status(),
                    order.getCreatedAt(),
                    order.getId(),
                    order.getOrderNum(),
                    menuOrders,
                    price
            );

            totalOrders.add(totalOrder);
        }

        return totalOrders;
    }

}
