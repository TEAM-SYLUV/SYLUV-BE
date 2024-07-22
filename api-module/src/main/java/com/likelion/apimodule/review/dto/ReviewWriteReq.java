package com.likelion.apimodule.review.dto;

public record ReviewWriteReq(
        Long menuId,
        Integer rate,
        String content
) {
}
