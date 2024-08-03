package com.likelion.coremodule.order.domain;

import com.likelion.commonmodule.exception.common.BaseEntity;
import com.likelion.coremodule.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String orderNum;

    private String phoneNum;
    private Integer visitHour;
    private Integer visitMin;
    private String pickUpRoute;
}
