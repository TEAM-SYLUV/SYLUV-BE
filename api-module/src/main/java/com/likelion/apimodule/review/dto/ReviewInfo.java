package com.likelion.apimodule.review.dto;

public record ReviewInfo(Long reviewId,
                         String name,
                         String picture,
                         String rating,
                         String content,
                         String image,
                         String likeCount,
                         String storeName,
                         String menuName,
                         Integer beforeHours,
                         Integer beforeDay,
                         Integer beforeWeek,
                         Boolean isMine) {
}
