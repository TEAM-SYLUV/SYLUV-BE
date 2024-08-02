package com.likelion.apimodule.cart.presentation;

import com.likelion.apimodule.cart.application.CartFindUseCase;
import com.likelion.apimodule.cart.application.CartSaveUseCase;
import com.likelion.apimodule.cart.dto.CartInfo;
import com.likelion.apimodule.cart.dto.CartSaveReq;
import com.likelion.apimodule.cart.dto.CartUpdateReq;
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
@RequestMapping("/v1/cart")
@Validated
@Tag(name = "Cart", description = "Cart 관련 API")
public class CartController {

    private final CartFindUseCase cartFindUseCase;
    private final CartSaveUseCase cartSaveUseCase;

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
    @PostMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "나의 장바구니 저장 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "나의 장바구니 저장 API", description = "나의 장바구니 저장 API 입니다.")
    public ApplicationResponse<String> saveMyCart(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken,
                                                  @RequestBody CartSaveReq saveReq) {

        final String myCart = cartSaveUseCase.saveMyCart(accessToken, saveReq);
        return ApplicationResponse.ok("장바구니 저장 완료 -> " + myCart);
    }

    // 장바구니 추가
    @PutMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "나의 장바구니 추가 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "나의 장바구니 추가 API", description = "나의 장바구니 추가 API 입니다.")
    public ApplicationResponse<String> updateMyCart(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken,
                                                    @RequestBody List<CartUpdateReq> updateReqs) {

        cartSaveUseCase.updateMyCart(accessToken, updateReqs);
        return ApplicationResponse.ok("장바구니 수량 수정 완료");
    }

    // 장바구니 삭제
    @DeleteMapping("{cartId}/delete")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "나의 장바구니 삭제 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "나의 장바구니 삭제 API", description = "나의 장바구니 삭제 API 입니다.")
    public ApplicationResponse<String> deleteMyCart(@RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken,
                                                    @PathVariable Long cartId) {

        cartSaveUseCase.deleteMyCart(accessToken, cartId);
        return ApplicationResponse.ok("장바구니 삭제 완료");
    }

}
