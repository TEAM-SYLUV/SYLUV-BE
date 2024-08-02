package com.likelion.coremodule.payment.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public PaymentException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public PaymentException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}