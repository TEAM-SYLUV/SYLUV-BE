package com.likelion.coremodule.store.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class StoreException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public StoreException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public StoreException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}