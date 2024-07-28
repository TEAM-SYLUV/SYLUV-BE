package com.likelion.coremodule.store.exception;

import com.likelion.commonmodule.exception.common.ApiResponse;
import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements BaseErrorCode {

    NO_STORE_INFO(HttpStatus.BAD_REQUEST, "2000", "시장 정보가 존재하지 않습니다."),
    NO_STORE_CATEGORY(HttpStatus.BAD_REQUEST, "2000", "카테고리 정보를 잘못 입력하였습니다."),
    NO_STORE_VISIT(HttpStatus.BAD_REQUEST, "2000", "방문 기록이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ApiResponse<Void> getErrorResponse() {
        return null;
    }
}
