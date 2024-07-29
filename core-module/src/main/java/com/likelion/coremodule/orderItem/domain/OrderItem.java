package com.likelion.coremodule.orderItem.domain;

import com.likelion.commonmodule.exception.common.BaseEntity;
import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.order.domain.Order;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderitem_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
    private int price;


}