package com.likelion.apimodule.customer.presentation;

import com.likelion.apimodule.customer.application.CustomerFIndUseCase;
import com.likelion.apimodule.customer.application.CustomerSaveUseCase;
import com.likelion.apimodule.customer.dto.TotalOrder;
import com.likelion.commonmodule.exception.common.ApplicationResponse;
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
@RequestMapping("/v1/customer")
@Validated
@Tag(name = "Customer", description = "Customer 관련 API")
public class CustomerController {

    private final CustomerFIndUseCase customerFIndUseCase;
    private final CustomerSaveUseCase customerSaveUseCase;

    // 주문 전체 조회 (정후)
    @GetMapping("/{storeId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "주문 전체 조회",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "주문 전체 조회 API", description = "주문 전체 조회 API 입니다.")
    public ApplicationResponse<List<TotalOrder>> getTotalOrder(
            @PathVariable Long storeId
    ) {

        List<TotalOrder> orders = customerFIndUseCase.getTotalOrder(storeId);
        return ApplicationResponse.ok(orders);
    }

    // 접수하는 API (정후)
    @PostMapping("/{storeId}/{orderId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "주문 접숙하기",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "주문 접수 API", description = "주문 접수 API 입니다.")
    public ApplicationResponse<String> changeToPreparing(
            @PathVariable Long storeId,
            @PathVariable Long orderId
    ) {

        customerSaveUseCase.changeToPreparing(storeId, orderId);
        return ApplicationResponse.ok("주문을 접수했습니다.");
    }

    // 준비 완료 시키는 API (정후)
    @PostMapping("/{storeId}/{orderId}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "주문 접숙하기",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "주문 접수 API", description = "주문 접수 API 입니다.")
    public ApplicationResponse<String> changeToPrepared(
            @PathVariable Long storeId,
            @PathVariable Long orderId
    ) {

        customerSaveUseCase.changeToPrepared(storeId, orderId);
        return ApplicationResponse.ok("주문을 접수했습니다.");
    }

    // 메뉴 추가 API (소연)
}
