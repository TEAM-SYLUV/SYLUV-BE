package com.likelion.apimodule.review.application;

import com.likelion.apimodule.review.dto.ReviewWriteReq;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.commonmodule.image.service.AwsS3Service;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.review.domain.Review;
import com.likelion.coremodule.review.domain.ReviewLike;
import com.likelion.coremodule.review.exception.ReviewErrorCode;
import com.likelion.coremodule.review.exception.ReviewException;
import com.likelion.coremodule.review.service.ReviewQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewSaveUseCase {

    private final ReviewQueryService reviewQueryService;
    private final UserQueryService userQueryService;
    private final OrderQueryService orderQueryService;
    private final AwsS3Service awsS3Service;
    private final JwtUtil jwtUtil;

    public void saveReview(String accessToken, ReviewWriteReq writeReq, MultipartFile multipartFile) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        Order order = orderQueryService.findOrderById(writeReq.orderId());
        String imageUrl = awsS3Service.uploadFile(multipartFile);

        Review review = Review.builder().user(user).order(order).rating(writeReq.rate()).content(writeReq.content()).imageUrl(imageUrl).build();
        reviewQueryService.saveReview(review);
    }

    public void deleteReview(String accessToken, Long reviewId) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        Review review = reviewQueryService.findReviewById(reviewId);
        if (!Objects.equals(review.getUser().getUserId(), user.getUserId())) {
            throw new ReviewException(ReviewErrorCode.NO_REVIEW_MINE);
        } else {
            reviewQueryService.deleteReview(reviewId);
        }
    }

    public void saveReviewLike(Long reviewId, String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);
        Review review = reviewQueryService.findReviewById(reviewId);

        if (reviewQueryService.countReviewLike(reviewId) > 0) {
            throw new ReviewException(ReviewErrorCode.EXIST_REVIEW_LIKE);
        } else {
            final ReviewLike reviewLike = ReviewLike.builder().user(user).review(review).build();
            reviewQueryService.saveReviewLike(reviewLike);
        }

    }
}
