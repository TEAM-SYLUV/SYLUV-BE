package com.likelion.coremodule.market.domain;

import com.likelion.commonmodule.exception.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Market extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long id;

    private String name;
    private String description;
    private String startHour;
    private String closeHour;
    private String location;
    private String contact;
    private String imageUrl;
    private Integer xloc;
    private Integer yloc;

}
