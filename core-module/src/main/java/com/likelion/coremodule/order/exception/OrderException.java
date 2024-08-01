package com.likelion.coremodule.order.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class OrderException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public OrderException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public OrderException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}