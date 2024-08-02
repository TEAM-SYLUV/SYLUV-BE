package com.likelion.apimodule.payment.dto.request;

public record ApproveFinalReq(
        Integer amount,
        String orderId,
        String paymentKey
) {
}
