package com.likelion.coremodule.market.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class MarketException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public MarketException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public MarketException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}