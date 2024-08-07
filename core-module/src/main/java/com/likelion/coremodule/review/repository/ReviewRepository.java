package com.likelion.coremodule.review.repository;

import com.likelion.coremodule.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByOrderId(Long orderId);

    Review findReviewByOrderIdAndUserUserId(Long orderId, Long userId);

    void deleteAllByOrderId(Long orderId);
}
