package com.likelion.apimodule.review.dto;

import java.util.List;

public record ReviewInfo(Long reviewId,
                         String name,
                         String picture,
                         String rating,
                         String content,
                         List<String> image,
                         String likeCount,
                         String storeName,
                         List<String> menuName,
                         Integer beforeHours,
                         Integer beforeDay,
                         Integer beforeWeek,
                         Boolean isMine,
                         Boolean helpfulYn) {
}
