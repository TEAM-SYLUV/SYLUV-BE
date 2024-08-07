package com.likelion.coremodule.cart.domain;

import com.likelion.commonmodule.exception.common.BaseEntity;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer quantity;

    public void setCartQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
