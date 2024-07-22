package com.likelion.commonmodule.image.exception;

import com.likelion.commonmodule.exception.common.BaseErrorCode;

public class FileUploadException extends ImageException {
    public FileUploadException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public FileUploadException(BaseErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
