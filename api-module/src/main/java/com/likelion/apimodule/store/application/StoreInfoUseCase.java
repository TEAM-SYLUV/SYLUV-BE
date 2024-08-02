package com.likelion.apimodule.store.application;

import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.apimodule.store.dto.MenuDetailDTO;
import com.likelion.apimodule.store.dto.StoreInfo;
import com.likelion.apimodule.store.dto.StoreResponse;
import com.likelion.apimodule.store.mapper.MenuMapper;
import com.likelion.coremodule.cart.domain.Cart;
import com.likelion.coremodule.cart.service.CartQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.domain.OrderItem;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.review.domain.Review;
import com.likelion.coremodule.review.service.ReviewQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.domain.StoreCategory;
import com.likelion.coremodule.store.exception.StoreErrorCode;
import com.likelion.coremodule.store.exception.StoreException;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreInfoUseCase {

    private final StoreQueryService storeQueryService;
    private final MenuQueryService menuQueryService;
    private final UserQueryService userQueryService;
    private final CartQueryService cartQueryService;
    private final ReviewQueryService reviewQueryService;
    private final JwtUtil jwtUtil;
    private final OrderQueryService orderQueryService;

    public List<StoreInfo> findStoreInfo() {

        List<Store> storeList = storeQueryService.findAllStore();
        List<StoreInfo> storeInfoList = new ArrayList<>();

        for (Store store : storeList) {
            List<Menu> menus = menuQueryService.findMenusByStoreId(store.getId());
            List<MenuDetailDTO> menuDetails = menus.stream()
                    .map(menu -> new MenuDetailDTO(
                            menu.getId(),
                            menu.getName(),
                            menu.getPrice(),
                            menu.getContent(),
                            menu.getImageUrl()))
                    .toList();

            // 리뷰 관련 cnt, avg
            List<Review> reviews = new ArrayList<>();

            for (Menu m : menus) {

                List<OrderItem> items = orderQueryService.findOrderItemsByMenuId(m.getId());
                for (OrderItem item : items) {
                    if (orderQueryService.countOrderById(item.getOrder().getId()) > 0) {
                        Order order = orderQueryService.findOrderById(item.getOrder().getId());

                        List<Review> review = reviewQueryService.findAllByOrderId(order.getId());
                        reviews.addAll(review);
                    }
                }
            }

            final StoreInfo storeInfo = getStoreInfo(store, reviews, menuDetails);
            storeInfoList.add(storeInfo);
        }

        return storeInfoList;
    }

    public MenuDetailDTO findMenuById(Long menuId) {

        Menu menu = menuQueryService.findMenuById(menuId);
        return MenuMapper.toInfoDTO(menu);
    }

    private StoreInfo getStoreInfo(Store store, List<Review> reviews, List<MenuDetailDTO> menuDetails) {
        int reviewCount = reviews.size();
        double ratingAvg = reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);

        return new StoreInfo(
                store.getId(),
                store.getName(),
                reviewCount,
                ratingAvg,
                store.getLocation(),
                store.getOpenHours(),
                store.getCloseHours(),
                store.getCategory(),
                store.getContact(),
                store.getImageUrl(),
                menuDetails
        );
    }

    public List<StoreResponse> findStoreByFilter(String search, String category) {

        List<StoreResponse> response = new ArrayList<>();
        List<Store> storeList = storeQueryService.findAllStore();

        if ((search == null || search.isEmpty()) && (category == null || category.isEmpty())) {

            for (Store store : storeList) {
                StoreResponse ex = new StoreResponse(store.getId(), store.getName(), store.getCategory(), store.getDescription(), store.getLocation(), store.getOpenHours(), store.getImageUrl());
                response.add(ex);
            }
            return response;
        }

        StoreCategory storeCategory = null;
        if (category != null && !category.isEmpty()) {
            try {
                storeCategory = StoreCategory.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new StoreException(StoreErrorCode.NO_STORE_CATEGORY);
            }
        }

        final StoreCategory finalStoreCategory = storeCategory;

        final List<Store> list = storeList.stream()
                .filter(store -> (search == null || search.isEmpty() || store.getName().contains(search)))
                .filter(store -> (finalStoreCategory == null || store.getCategory() == finalStoreCategory))
                .toList();

        for (Store store : list) {
            StoreResponse ex = new StoreResponse(store.getId(), store.getName(), store.getCategory(), store.getDescription(), store.getLocation(), store.getOpenHours(), store.getImageUrl());
            response.add(ex);
        }

        return response;
    }

    public void addToCart(Long menuId, String accessToken) {

        Menu menu = menuQueryService.findMenuById(menuId);

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        final Cart cart = Cart.builder().user(user).menu(menu).build();
        cartQueryService.saveCart(cart);
    }
}
