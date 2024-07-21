package com.likelion.apimodule.store.presentation;

import com.likelion.apimodule.store.application.StoreInfoUseCase;
import com.likelion.apimodule.store.dto.StoreInfo;
import com.likelion.commonmodule.exception.common.ApplicationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/store")
@Validated
@Tag(name = "Store", description = "Store 관련 API")
public class StoreController {

    private final StoreInfoUseCase storeInfoUseCase;

    // 가게 정보
    @GetMapping("/info")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "가게 정보 확인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "가게 정보 확인 API", description = "가게 정보 확인 API 입니다.")
    public ApplicationResponse<List<StoreInfo>> getStoreInfo() {

        List<StoreInfo> infos = storeInfoUseCase.findStoreInfo();
        return ApplicationResponse.ok(infos);
    }

    // 장바구니 추가

    // 리뷰 조회

    // 리뷰 작성
}
