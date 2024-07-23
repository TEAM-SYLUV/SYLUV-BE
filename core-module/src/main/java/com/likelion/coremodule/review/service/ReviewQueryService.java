package com.likelion.coremodule.review.service;

import com.likelion.coremodule.review.domain.Review;
import com.likelion.coremodule.review.domain.ReviewLike;
import com.likelion.coremodule.review.exception.ReviewErrorCode;
import com.likelion.coremodule.review.exception.ReviewException;
import com.likelion.coremodule.review.repository.ReviewLikeRepository;
import com.likelion.coremodule.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public List<Review> findAllReviews() {
        return reviewRepository.findAllReviews();
    }

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewException(ReviewErrorCode.NO_MENU_INFO));
    }

    public List<Review> findReviewsByStoreId(Long menuId) {
        return reviewRepository.findReviewsByMenuId(menuId);
    }

    public Long findLikeCountByReviewId(Long reviewId) {
        return reviewLikeRepository.countByReviewId(reviewId);
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public void saveReviewLike(ReviewLike reviewLike) {
        reviewLikeRepository.save(reviewLike);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
