package com.likelion.apimodule.payment.service;

import com.likelion.apimodule.payment.dto.request.ApprovalRequest;
import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.coremodule.cart.service.CartQueryService;
import com.likelion.coremodule.market.service.MarketQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.domain.OrderItem;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final OrderQueryService orderQueryService;
    private final StoreQueryService storeQueryService;
    private final MarketQueryService marketQueryService;
    private final UserQueryService userQueryService;
    private final JwtUtil jwtUtil;

    private static final DateTimeFormatter ORDER_NUMBER_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    private final MenuQueryService menuQueryService;
    private final CartQueryService cartQueryService;

    public void approval(String accessToken, ApprovalRequest request) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        List<Menu> menuList = new ArrayList<>();
        for (Long id : request.cartIds()) {
            Menu menu = menuQueryService.findMenuById(id);
            menuList.add(menu);

            cartQueryService.deleteCartByUserIdAndCartId(user.getUserId(), id);
        }

        Store store = storeQueryService.findStoreById(menuList.get(0).getId());

        // 토스 페이 결제 승인
//        TossPaymentResponse tossPaymentResponse = paymentClient.confirmPayment(request);

        // 방문 리스트 결제 완료로 저장 + 주문 테이블 저장
        marketQueryService.saveVisitListToPayment(store.getId(), user.getEmail());

        final Order order = Order.builder().orderNum(request.orderNum()).user(user).
                phoneNum(request.phoneNum()).pickUpRoute(request.pickUpRoute()).
                visitHour(request.visitHour()).visitMin(request.visitMin()).build();
        orderQueryService.saveOrder(order);

        for (Menu value : menuList) {

            Long m = value.getId();
            Menu menu = menuQueryService.findMenuById(m);

            final OrderItem orderItem = OrderItem.builder().order(order).menu(menu).quantity(request.amount()).build();
            orderQueryService.saveOrderItem(orderItem);
        }
    }

    public String generateOrderNumber(LocalDateTime createdAt) {
        String datePart = createdAt.format(ORDER_NUMBER_DATE_FORMAT);
        String randomAlphaNumeric = generateRandomAlphaNumeric();
        return datePart + randomAlphaNumeric;
    }

    private String generateRandomAlphaNumeric() {
        StringBuilder alphaNumeric = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(ALPHANUMERIC.length());
            alphaNumeric.append(ALPHANUMERIC.charAt(index));
        }
        return alphaNumeric.toString();
    }

}
