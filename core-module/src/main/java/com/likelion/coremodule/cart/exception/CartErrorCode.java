package com.likelion.coremodule.cart.exception;

import com.likelion.commonmodule.exception.common.ApiResponse;
import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements BaseErrorCode {

    NO_CART_INFO(HttpStatus.BAD_REQUEST, "2000", "장바구니 안에 상품이 존재하지 않습니다."),
    EXISTED_CART_INFO(HttpStatus.BAD_REQUEST, "2000", "장바구니 안에 이미 동일 상품이 존재합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ApiResponse<Void> getErrorResponse() {
        return null;
    }
}
