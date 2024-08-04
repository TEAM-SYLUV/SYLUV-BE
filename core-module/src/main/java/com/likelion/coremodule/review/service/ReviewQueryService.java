package com.likelion.coremodule.review.service;

import com.likelion.coremodule.review.domain.Review;
import com.likelion.coremodule.review.domain.ReviewImage;
import com.likelion.coremodule.review.domain.ReviewLike;
import com.likelion.coremodule.review.exception.ReviewErrorCode;
import com.likelion.coremodule.review.exception.ReviewException;
import com.likelion.coremodule.review.repository.ReviewImageRepository;
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
    private final ReviewImageRepository reviewImageRepository;

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewException(ReviewErrorCode.NO_MENU_INFO));
    }

    public ReviewLike findReviewLike(Long reviewId, Long userId) {
        return reviewLikeRepository.findReviewLikeByReviewIdAndUserUserId(reviewId, userId);
    }

    public List<ReviewImage> findImagesByReviewId(Long reviewId) {
        return reviewImageRepository.findAllByReviewId(reviewId);
    }

    public List<Review> findAllByOrderId(Long orderId) {
        return reviewRepository.findAllByOrderId(orderId);
    }

    public Review findReviewByOrderAndUser(Long orderId, Long userId) {
        return reviewRepository.findReviewByOrderIdAndUserUserId(orderId, userId);
    }

    public Integer countAllLikeCount(Long reviewId) {
        return reviewLikeRepository.countAllByReviewId(reviewId);
    }

    public Integer countLikeCountByMine(Long userId, Long reviewId) {
        return reviewLikeRepository.countAllByUserUserIdAndReviewId(userId, reviewId);
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public void saveReviewImage(ReviewImage image) {
        reviewImageRepository.save(image);
    }

    public void saveReviewLike(ReviewLike reviewLike) {
        reviewLikeRepository.save(reviewLike);
    }

    public Integer countReviewLikeAndUserId(Long reviewId, Long userId) {
        return reviewLikeRepository.countAllByUserUserIdAndReviewId(userId, reviewId);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);

    }

    public void deleteReviewLike(Long id) {
        reviewLikeRepository.deleteById(id);
    }
}