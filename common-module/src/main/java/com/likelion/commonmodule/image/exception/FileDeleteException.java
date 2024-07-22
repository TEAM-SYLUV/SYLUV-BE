package com.likelion.commonmodule.image.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;

public class FileDeleteException extends ImageException {
    public FileDeleteException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public FileDeleteException(BaseErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
