package com.likelion.apimodule.store.presentation;

import com.likelion.apimodule.store.application.StoreInfoUseCase;
import com.likelion.apimodule.store.dto.StoreInfo;
import com.likelion.commonmodule.exception.common.ApplicationResponse;
import com.likelion.commonmodule.security.util.AuthConsts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{menuId}/info")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "가게 메뉴 정보 확인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "가게 메뉴 정보 확인 API", description = "가게 메뉴 정보 확인 API 입니다.")
    public ApplicationResponse<StoreInfo> getStoreInfoByMenuId(@PathVariable Long menuId) {

        StoreInfo info = storeInfoUseCase.findStoreInfoByMenuId(menuId);
        return ApplicationResponse.ok(info);
    }

    // 장바구니 추가
    @PostMapping("/{menuId}/addcart")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "장바구니 추가 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "장바구니 추가 API", description = "장바구니 추가 API 입니다.")
    public ApplicationResponse<String> addToCart(@PathVariable Long menuId,
                                                 @RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken) {

        storeInfoUseCase.addToCart(menuId, accessToken);
        return ApplicationResponse.ok("장바구니 추가 완료");
    }

}
