package com.likelion.apimodule.review.application;

import com.likelion.apimodule.review.dto.ReviewInfo;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.review.domain.Review;
import com.likelion.coremodule.review.service.ReviewQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewFindUseCase {

    private final ReviewQueryService reviewQueryService;
    private final StoreQueryService storeQueryService;

    public List<ReviewInfo> findAllReviews() {

        List<Review> allReviews = reviewQueryService.findAllReviews();
        List<ReviewInfo> reviewInfos = new ArrayList<>();

        for (Review review : allReviews) {
            User user = review.getUser();
            Menu menu = review.getMenu();

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

            ReviewInfo reviewInfo = new ReviewInfo(id, name, picture, rating, content, image, likeCount, storeName, menuName);
            reviewInfos.add(reviewInfo);
        }

        return reviewInfos;
    }
}

