package com.likelion.coremodule.VisitList.exception;

import com.likelion.commonmodule.exception.common.ApiResponse;
import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VisitErrorCode implements BaseErrorCode {

    EXIST_VISIT_LIST_INFO(HttpStatus.BAD_REQUEST, "2000", "방문 리스트 정보가 이미 존재합니다."),
    NO_VISIT_LIST_INFO(HttpStatus.BAD_REQUEST, "2000", "해당 방문 리스트가 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ApiResponse<Void> getErrorResponse() {
        return null;
    }
}
