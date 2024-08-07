package com.likelion.coremodule.order.repository;

import com.likelion.coremodule.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findOrderItemsByOrderId(Long orderId);

    List<OrderItem> findOrderItemsByMenuId(Long menuId);

    void deleteAllByOrderId(Long orderId);
}
