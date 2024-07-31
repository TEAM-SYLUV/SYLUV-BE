package com.likelion.apimodule.home.presentation;

import com.likelion.apimodule.home.application.HomeFindUseCase;
import com.likelion.apimodule.home.application.HomeSaveUseCase;
import com.likelion.apimodule.home.dto.HomeInfo;
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
@RequestMapping("/v1/home")
@Validated
@Tag(name = "Home", description = "Home 관련 API")
public class HomeController {

    private final HomeFindUseCase homeFindUseCase;
    private final HomeSaveUseCase homeSaveUseCase;

    // qr 시 시장 조회수 증가
    @PostMapping("/{storeId}/qrvisit")
    public ApplicationResponse<String> updateQrVisit(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken,
                                                     @PathVariable Long storeId) {

        homeSaveUseCase.updateQrVisit(accessToken, storeId);
        return ApplicationResponse.ok("qr 조회수 증가 + 방문 상태 변경");
    }

    // 시장 리스트 나열
    @GetMapping
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
    public ApplicationResponse<HomeInfo> findHomeInfo(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken) {

        final HomeInfo marketLists = homeFindUseCase.findMarketLists(accessToken);
        return ApplicationResponse.ok(marketLists);
    }

    // 시장 리스트 검색
    @GetMapping("/search")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "시장 리스트 전체 검색 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "시장 리스트 검색 API", description = "시장 리스트 검색 API 입니다.")
    public ApplicationResponse<List<String>> findALlMarkets() {

        List<String> names = homeFindUseCase.findAllMarkets();
        return ApplicationResponse.ok(names);
    }

    // 가까운 시장 확인
    @GetMapping("/nearmarket")
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
    public ApplicationResponse<String> findNearestMarket(@RequestParam String xloc, @RequestParam String yloc) {

//        String nearestMarket = homeFindUseCase.findNearestMarket(xloc, yloc);
        return ApplicationResponse.ok("광장시장");
    }
}
