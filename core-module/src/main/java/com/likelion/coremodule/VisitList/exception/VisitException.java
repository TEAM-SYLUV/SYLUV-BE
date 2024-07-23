package com.likelion.coremodule.VisitList.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class VisitException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public VisitException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public VisitException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}