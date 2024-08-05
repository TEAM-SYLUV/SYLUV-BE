package com.likelion.apimodule.payment.dto.request;

import java.util.List;

public record ApprovalRequest(
        List<Long> cartIds,
        String orderNum,
        String paymentKey,
        Integer amount,
        String pickUpRoute,
        Integer visitHour,
        Integer visitMin,
        String phoneNum
) {
}
