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

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_status", nullable = false)
    private VisitStatus visit_status;

    public void updateToPayment() {
        this.visit_status = VisitStatus.PAYMENT;
    }

    public void updateToPreparing() {
        if (this.visit_status != VisitStatus.PAYMENT) {
            throw new IllegalStateException("Visit can only be updated to PREPARING from PAYMENT status");
        }
        this.visit_status = VisitStatus.PREPARING;
    }

    public void updateToPrepared() {
        if (this.visit_status != VisitStatus.PREPARING) {
            throw new IllegalStateException("Visit can only be updated to PREPARED from PREPARING status");
        }
        this.visit_status = VisitStatus.PREPARED;
    }

    public void updateToVisited() {
        if (this.visit_status != VisitStatus.PREPARED) {
            throw new IllegalStateException("Visit can only be updated to VISITED from PREPARED status");
        }
        this.visit_status = VisitStatus.VISITED;
    }
}
