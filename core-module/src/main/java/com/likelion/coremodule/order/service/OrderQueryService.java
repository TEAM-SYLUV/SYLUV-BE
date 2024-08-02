package com.likelion.coremodule.order.service;

import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.domain.OrderItem;
import com.likelion.coremodule.order.exception.OrderErrorCode;
import com.likelion.coremodule.order.exception.OrderException;
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

    public Integer countOrderById(Long orderId) {
        return orderRepository.countAllById(orderId);
    }

    public List<OrderItem> findOrderItemsByMenuId(Long menuId) {
        return orderItemRepository.findOrderItemsByMenuId(menuId);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderException(OrderErrorCode.NO_ORDER_INFO));
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void deleteMyOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        orderItemRepository.deleteAllByOrderId(orderId);
    }
}
