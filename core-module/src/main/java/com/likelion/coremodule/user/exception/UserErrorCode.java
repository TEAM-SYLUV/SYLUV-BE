package com.likelion.coremodule.user.exception;

import com.likelion.commonmodule.exception.common.ApiResponse;
import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    No_USER_INFO(HttpStatus.BAD_REQUEST, "2000", "사용자 정보가 존재하지 않습니다."),
    EMPTY_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST, "2000", "토큰 정보가 비어 있습니다."),
    INVALID_AUTHORIZATION_TYPE(HttpStatus.BAD_REQUEST, "2000", "타입이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ApiResponse<Void> getErrorResponse() {
        return null;
    }
}
