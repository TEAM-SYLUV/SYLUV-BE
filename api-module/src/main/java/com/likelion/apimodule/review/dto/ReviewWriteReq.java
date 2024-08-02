package com.likelion.apimodule.review.dto;

public record ReviewWriteReq(
        Long orderId,
        Integer rate,
        String content
) {
}
