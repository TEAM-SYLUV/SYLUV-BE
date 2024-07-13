package com.likelion.commonmodule.exception.jwt;

import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class SecurityCustomException extends RuntimeException {

	private final BaseErrorCode errorCode;

	private final Throwable cause;

	public SecurityCustomException(BaseErrorCode errorCode) {
		this.errorCode = errorCode;
		this.cause = null;
	}

	public SecurityCustomException(BaseErrorCode errorCode, Throwable cause) {
		this.errorCode = errorCode;
		this.cause = cause;
	}
}
