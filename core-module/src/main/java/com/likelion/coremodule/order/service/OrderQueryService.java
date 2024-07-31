package com.likelion.coremodule.order.service;

import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<Order> findOrderByUserId(Long userId) {
        return orderRepository.findOrdersByUserUserId(userId);
    }
}
