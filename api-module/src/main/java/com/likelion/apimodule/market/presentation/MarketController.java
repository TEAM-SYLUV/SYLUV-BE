package com.likelion.apimodule.market.presentation;

import com.likelion.apimodule.market.application.MarketInfoUseCase;
import com.likelion.apimodule.market.dto.MarketInfo;
import com.likelion.apimodule.market.dto.VisitListInfo;
import com.likelion.apimodule.store.application.StoreInfoUseCase;
import com.likelion.apimodule.store.dto.StoreResponse;
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
@RequestMapping("/v1/market")
@Validated
@Tag(name = "Market", description = "Market 관련 API")
public class MarketController {

    private final MarketInfoUseCase marketInfoUseCase;
    private final StoreInfoUseCase storeInfoUseCase;

    // 시장 정보
    @GetMapping("/{marketId}/info")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "시장 정보 확인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "시장 정보 확인 API", description = "시장 정보 확인 API 입니다.")
    public ApplicationResponse<MarketInfo> getMarketInfo(@PathVariable Long marketId) {

        MarketInfo infos = marketInfoUseCase.findMarketInfo(marketId);
        return ApplicationResponse.ok(infos);
    }

    // 가게 검색
    @GetMapping("/store")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "가게 검색 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "가게 검색 API", description = "가게 검색 API 입니다.")
    public ApplicationResponse<List<StoreResponse>> findStoreByFilter(@RequestParam(required = false) String search,
                                                                      @RequestParam(required = false) String category) {

        final List<StoreResponse> storeByFilter = storeInfoUseCase.findStoreByFilter(search, category);
        return ApplicationResponse.ok(storeByFilter);
    }

    // 방문 리스트 추가
    @PostMapping("/{storeId}/visitlist")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "방문 리스트 추가 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "방문 리스트 추가 API", description = "방문 리스트 추가 API 입니다.")
    public ApplicationResponse<String> saveVisitList(@PathVariable Long storeId,
                                                     @RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken) {

        marketInfoUseCase.saveVisitList(storeId, accessToken);
        return ApplicationResponse.ok("방문 리스트 추가 완료");
    }

    // 전체 방문 리스트 조회
    @GetMapping("/visitlist")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "방문 리스트 조회 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "방문 리스트 조회 API", description = "방문 리스트 조회 API 입니다.")
    public ApplicationResponse<List<VisitListInfo>> findVisitList() {

        final List<VisitListInfo> visitList = marketInfoUseCase.findVisitList();
        return ApplicationResponse.ok(visitList);
    }

    // 방문 리스트 삭제
    @DeleteMapping("/{visitListId}/visitlist/delete")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "방문 리스트 삭제 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "방문 리스트 삭제 API", description = "방문 리스트 삭제 API 입니다.")
    public ApplicationResponse<String> deleteVisitList(
            @PathVariable Long visitListId) {

        marketInfoUseCase.deleteVisitList(visitListId);
        return ApplicationResponse.ok("방문 리스트 삭제 성공");
    }
}
