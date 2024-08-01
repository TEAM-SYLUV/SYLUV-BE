package com.likelion.apimodule.order.presentation;

import com.likelion.apimodule.order.application.OrderFindUseCase;
import com.likelion.apimodule.order.dto.OrderDetail;
import com.likelion.apimodule.order.dto.OrderInfo;
import com.likelion.commonmodule.exception.common.ApplicationResponse;
import com.likelion.commonmodule.security.util.AuthConsts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/order")
@Validated
@Tag(name = "Order", description = "Order 관련 API")
public class OrderController {

    private final OrderFindUseCase orderFindUseCase;

    // 주문내역 조회
    @GetMapping
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "나의 주문내역 확인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "나의 주문내역 확인 API", description = "나의 주문내역 API 입니다.")
    public ApplicationResponse<Map<LocalDate, List<OrderInfo>>> getMyOrderInfo(
            @RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken
    ) {

        Map<LocalDate, List<OrderInfo>> infos = orderFindUseCase.findAllOrdersByDate(accessToken);
        return ApplicationResponse.ok(infos);
    }

    // 주문 상세
    @GetMapping("/{orderId}/detail")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "나의 주문상세 확인 성공",
                            useReturnTypeSchema = true
                    )
            }
    )
    @Operation(summary = "나의 주문내역 확인 API", description = "나의 주문내역 API 입니다.")
    public ApplicationResponse<OrderDetail> findMyOrderDetail(
            @RequestHeader(AuthConsts.ACCESS_TOKEN_HEADER) String accessToken,
            @PathVariable Long orderId
    ) {

        OrderDetail orderDetail = orderFindUseCase.findMyOrderDetail(accessToken, orderId);
        return ApplicationResponse.ok(orderDetail);
    }

    // 주문하기

    // 토스 결제하기

}
