package com.likelion.apimodule.review.application;

import com.likelion.apimodule.review.dto.ReviewInfo;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.domain.OrderItem;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.review.domain.Review;
import com.likelion.coremodule.review.domain.ReviewImage;
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
    private final OrderQueryService orderQueryService;
    private final JwtUtil jwtUtil;

    public List<ReviewInfo> findAllReviews(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User myUser = userQueryService.findByEmail(email);

        List<Order> orderList = orderQueryService.findOrderByUserId(myUser.getUserId());
        List<Review> allReviews = new ArrayList<>();
        for (Order order : orderList) {
            List<Review> itemreviews = reviewQueryService.findAllByOrderId(order.getId());
            allReviews.addAll(itemreviews);
        }

        List<ReviewInfo> reviewInfos = new ArrayList<>();

        for (Review review : allReviews) {

            User user = userQueryService.findById(review.getUser().getUserId());

            List<OrderItem> items = orderQueryService.findOrderItemByOrderId(review.getOrder().getId());
            List<Menu> menus = new ArrayList<>();
            List<String> menuNameList = new ArrayList<>();
            for (OrderItem item : items) {
                Menu menu = menuQueryService.findMenuById(item.getMenu().getId());
                menuNameList.add(menu.getName());
                menus.add(menu);
            }

            String name = user.getName();
            String picture = user.getPicture();
            Long id = review.getId();
            String rating = review.getRating().toString();
            String content = review.getContent();

            List<ReviewImage> images = reviewQueryService.findImagesByReviewId(review.getId());
            List<String> reviewImages = new ArrayList<>();
            for (ReviewImage image : images) {
                reviewImages.add(image.getImageUrl());
            }

            String likeCount = reviewQueryService.findLikeCountByReviewId(review.getId()).toString();

            Store store = storeQueryService.findStoreById(menus.get(0).getStore().getId());
            String storeName = store.getName();

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime reviewTime = review.getCreatedAt();
            Integer hourDifference = (int) Duration.between(reviewTime, now).toHours();
            int dayDifference = (int) Duration.between(reviewTime, now).toDays();
            Integer weekDifference = dayDifference / 7;

            boolean isMine = user.getUserId().equals(myUser.getUserId());
            boolean helpfulYn = reviewQueryService.countLikeCountByMine(user.getUserId(), review.getId()) > 0;

            ReviewInfo reviewInfo = new ReviewInfo(id, name, picture, rating, content, reviewImages,
                    likeCount, storeName, menuNameList, hourDifference, dayDifference, weekDifference, isMine, helpfulYn);
            reviewInfos.add(reviewInfo);
        }

        reviewInfos.sort(Comparator.comparing(ReviewInfo::isMine)
                .thenComparingInt(info -> Integer.parseInt(info.likeCount()))
                .reversed());

        return reviewInfos;
    }

}

