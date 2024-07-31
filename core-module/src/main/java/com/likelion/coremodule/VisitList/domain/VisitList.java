package com.likelion.coremodule.VisitList.domain;

import com.likelion.commonmodule.exception.common.BaseEntity;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class VisitList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitlist_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate visitedDate;

    private VisitStatus visit_status;

    public void updateToPrepared() {
        this.visit_status = VisitStatus.PREPARED;
    }

    public void updateToVisited() {
        this.visit_status = VisitStatus.VISITED;
    }
}
