package com.likelion.apimodule.payment.dto.response;

import lombok.Getter;

public enum PaymentMethod {

    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    EASY_PAYMENT("간편결제"),
    MOBILE("휴대폰"),
    ACCOUNT_TRANSFER("계좌이체"),
    CULTURE_GIFT_CARD("문화상품권"),
    BOOK_CULTURE_GIFT_CARD("도서문화상품권"),
    GAME_CULTURE_GIFT_CARD("게임문화상품권"),
    CASH("테스트용");

    @Getter
    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }
}
