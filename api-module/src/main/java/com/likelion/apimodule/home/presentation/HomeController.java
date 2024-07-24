package com.likelion.apimodule.home.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/home")
@Validated
@Tag(name = "Home", description = "Home 관련 API")
public class HomeController {

    // qr 시 시장 조회수 증가
//    @GetMapping("/{marketId}/info")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "시장 정보 확인 성공",
//                            useReturnTypeSchema = true
//                    )
//            }
//    )
//    @Operation(summary = "시장 정보 확인 API", description = "시장 정보 확인 API 입니다.")

    // 시장 리스트 검색

    // 시장 리스트 나열
}
