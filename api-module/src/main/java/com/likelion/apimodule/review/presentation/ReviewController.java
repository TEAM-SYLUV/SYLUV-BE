package com.likelion.apimodule.review.presentation;

import com.likelion.apimodule.review.application.ReviewFindUseCase;
import com.likelion.apimodule.review.application.ReviewSaveUseCase;
import com.likelion.apimodule.review.dto.ReviewInfo;
import com.likelion.apimodule.review.dto.ReviewWriteReq;
import com.likelion.commonmodule.exception.common.ApplicationResponse;
import com.likelion.commonmodule.security.util.AuthConsts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/review")
@Validated
@Tag(name = "Review", description = "Review 관련 API")
public class ReviewController {

    private final ReviewFindUseCase reviewFindUseCase;
    private final ReviewSaveUseCase reviewSaveUseCase;

    // 리뷰 조회
    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "전체 리뷰 확인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "전체 리뷰 확인 API", description = "전체 리뷰 확인 API 입니다.")
    public ApplicationResponse<List<ReviewInfo>> getReviewInfo(
            @RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken
    ) {

        List<ReviewInfo> infos = reviewFindUseCase.findAllReviews(accessToken);
        return ApplicationResponse.ok(infos);
    }

    // 리뷰 저장
    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "리뷰 작성 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "리뷰 작성 API", description = "리뷰 작성 API 입니다.")
    public ApplicationResponse<String> writeReview(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken,
                                                   @RequestPart(value = "dto") ReviewWriteReq writeReq,
                                                   @RequestPart(value = "file") MultipartFile multipartFile) {

        reviewSaveUseCase.saveReview(accessToken, writeReq, multipartFile);
        return ApplicationResponse.ok("리뷰가 정상적으로 작성되었습니다.");
    }

    // 리뷰 삭제
    @DeleteMapping("{reviewId}/delete")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "리뷰 삭제 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "리뷰 삭제 API", description = "리뷰 삭제 API 입니다.")
    public ApplicationResponse<String> deleteReview(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken,
                                                    @PathVariable Long reviewId) {

        reviewSaveUseCase.deleteReview(accessToken, reviewId);
        return ApplicationResponse.ok("리뷰가 정상적으로 삭제되었습니다.");
    }

    // 도움이 돼요 저장
    @PostMapping("/{reviewId}/like")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "도움이 돼요 저장 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "도움이 돼요 저장 API", description = "도움이 돼요 저장 API 입니다.")
    public ApplicationResponse<String> saveReviewLike(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken,
                                                      @PathVariable Long reviewId) {

        reviewSaveUseCase.saveReviewLike(reviewId, accessToken);
        return ApplicationResponse.ok("도움이 돼요 저장 완료");
    }
}
