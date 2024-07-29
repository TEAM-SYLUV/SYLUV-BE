package com.likelion.coremodule.order.repository;

import com.likelion.coremodule.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
