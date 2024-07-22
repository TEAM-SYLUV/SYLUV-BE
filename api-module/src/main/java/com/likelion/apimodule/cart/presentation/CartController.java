package com.likelion.apimodule.cart.presentation;

import com.likelion.apimodule.cart.application.CartFindUseCase;
import com.likelion.apimodule.cart.dto.CartInfo;
import com.likelion.commonmodule.exception.common.ApplicationResponse;
import com.likelion.commonmodule.security.util.AuthConsts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/cart")
@Validated
@Tag(name = "Cart", description = "Cart 관련 API")
public class CartController {

    private final CartFindUseCase cartFindUseCase;

    // 장바구니 조회
    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "나의 장바구니 확인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "나의 장바구니 확인 API", description = "나의 장바구니 API 입니다.")
    public ApplicationResponse<List<CartInfo>> getMyCartInfo(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken
    ) {

        List<CartInfo> infos = cartFindUseCase.findAllCarts(accessToken);
        return ApplicationResponse.ok(infos);
    }

    // 장바구니 저장

    // 장바구니 삭제
}
