package com.likelion.apimodule.review.application;

import com.likelion.apimodule.review.dto.ReviewInfo;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.review.domain.Review;
import com.likelion.coremodule.review.service.ReviewQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewFindUseCase {

    private final ReviewQueryService reviewQueryService;
    private final StoreQueryService storeQueryService;
    private final UserQueryService userQueryService;
    private final MenuQueryService menuQueryService;
    private final JwtUtil jwtUtil;

    public List<ReviewInfo> findAllReviews(String accessToken) {

        List<Review> allReviews = reviewQueryService.findAllReviews();
        List<ReviewInfo> reviewInfos = new ArrayList<>();

        String email = jwtUtil.getEmail(accessToken);
        User myUser = userQueryService.findByEmail(email);

        for (Review review : allReviews) {

            User user = userQueryService.findById(review.getUser().getUserId());
            Menu menu = menuQueryService.findMenuById(review.getMenu().getId());

            String name = user.getName();
            String picture = user.getPicture();
            Long id = review.getId();
            String rating = review.getRating().toString();
            String content = review.getContent();
            String image = review.getImageUrl();
            String likeCount = reviewQueryService.findLikeCountByReviewId(review.getId()).toString();

            Store store = storeQueryService.findStoreById(menu.getStore().getId());
            String storeName = store.getName();
            String menuName = menu.getName();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime reviewTime = review.getCreatedAt();
            Integer hourDifference = (int) Duration.between(reviewTime, now).toHours();
            int dayDifference = (int) Duration.between(reviewTime, now).toDays();
            Integer weekDifference = dayDifference / 7;

            boolean isMine = user.getUserId().equals(myUser.getUserId());
            ReviewInfo reviewInfo = new ReviewInfo(id, name, picture, rating, content, image,
                    likeCount, storeName, menuName, hourDifference, dayDifference, weekDifference, isMine);
            reviewInfos.add(reviewInfo);
        }

        reviewInfos.sort(Comparator.comparing(ReviewInfo::isMine)
                .thenComparingInt(info -> Integer.parseInt(info.likeCount()))
                .reversed());

        return reviewInfos;
    }

}

