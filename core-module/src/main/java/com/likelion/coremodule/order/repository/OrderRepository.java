package com.likelion.coremodule.order.repository;

import com.likelion.coremodule.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByUserUserId(Long userId);

    Integer countAllById(Long id);
}
