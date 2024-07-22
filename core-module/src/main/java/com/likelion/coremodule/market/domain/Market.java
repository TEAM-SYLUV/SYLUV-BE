package com.likelion.coremodule.market.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long id;

    private String name;
    private String description;
    private String startHour;
    private String closeHour;
    private String contact;
    private String imageUrl;
}
