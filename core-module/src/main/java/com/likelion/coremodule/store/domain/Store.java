package com.likelion.coremodule.store.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import com.likelion.commonmodule.exception.common.BaseEntity;
import com.likelion.coremodule.market.domain.Market;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private Market market;

    private String name;
    private StoreCategory category;
    private String description;
    private String location;
    private String openHours;
    private String closeHours;
    private String contact;
    private String imageUrl;
    private String xloc;
    private String yloc;

    @JsonValue
    public String getCategoryValue() {
        return category.name();
    }
}
