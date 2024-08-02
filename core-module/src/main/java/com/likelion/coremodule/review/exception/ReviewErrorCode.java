package com.likelion.coremodule.review.exception;

import com.likelion.commonmodule.exception.common.ApiResponse;
import com.likelion.commonmodule.exception.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    NO_MENU_INFO(HttpStatus.BAD_REQUEST, "2000", "메뉴 정보가 존재하지 않습니다."),
    NO_REVIEW_INFO(HttpStatus.BAD_REQUEST, "2000", "리뷰가 존재하지 않습니다."),
    NO_REVIEW_MINE(HttpStatus.BAD_REQUEST, "2000", "본인의 작성 리뷰가 아닙니다."),
    EXIST_REVIEW_LIKE(HttpStatus.BAD_REQUEST, "2000", "이미 도움이 돼요에 참여하셨습니다.");;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ApiResponse<Void> getErrorResponse() {
        return null;
    }
}
