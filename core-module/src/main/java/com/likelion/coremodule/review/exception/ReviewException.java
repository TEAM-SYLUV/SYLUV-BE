package com.likelion.coremodule.review.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public ReviewException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public ReviewException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}