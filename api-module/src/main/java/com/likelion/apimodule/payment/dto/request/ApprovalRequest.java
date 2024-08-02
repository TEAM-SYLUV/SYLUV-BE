package com.likelion.apimodule.payment.dto.request;

import java.util.List;

public record ApprovalRequest(
        List<Long> menuIds,
        String paymentKey,
        Integer amount
) {
}
