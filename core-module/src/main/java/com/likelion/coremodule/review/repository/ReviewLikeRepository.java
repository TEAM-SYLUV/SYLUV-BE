package com.likelion.coremodule.review.repository;

import com.likelion.coremodule.review.domain.ReviewLike;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    @Query("SELECT COUNT(rl) FROM ReviewLike rl WHERE rl.review.id = :reviewId")
    Long countByReviewId(@Param("reviewId") Long reviewId);

    Integer countAllByUserUserIdAndReviewId(Long userId, Long reviewId);

    Integer countAllByReviewIdAndUserUserId(Long reviewId, Long userId);
}
