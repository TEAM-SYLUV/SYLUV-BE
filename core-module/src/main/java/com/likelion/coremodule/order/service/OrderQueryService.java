package com.likelion.coremodule.order.service;

import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.domain.OrderItem;
import com.likelion.coremodule.order.repository.OrderItemRepository;
import com.likelion.coremodule.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public List<Order> findOrderByUserId(Long userId) {
        return orderRepository.findOrdersByUserUserId(userId);
    }

    public List<OrderItem> findOrderItemByOrderId(Long orderId) {
        return orderItemRepository.findOrderItemsByOrderId(orderId);
    }
}
