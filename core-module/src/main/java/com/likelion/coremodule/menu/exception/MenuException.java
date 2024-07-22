package com.likelion.coremodule.menu.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class MenuException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public MenuException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public MenuException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}