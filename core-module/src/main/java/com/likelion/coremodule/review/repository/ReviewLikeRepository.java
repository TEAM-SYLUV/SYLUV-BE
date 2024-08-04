package com.likelion.coremodule.review.repository;

import com.likelion.coremodule.review.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    Integer countAllByUserUserIdAndReviewId(Long userId, Long reviewId);

    Integer countAllByReviewId(Long reviewId);
}
