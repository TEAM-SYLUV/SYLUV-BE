package com.likelion.apimodule.store.application;

import com.likelion.apimodule.security.util.JwtUtil;
import com.likelion.apimodule.store.dto.MenuDetailDTO;
import com.likelion.apimodule.store.dto.StoreInfo;
import com.likelion.apimodule.store.dto.StoreResponse;
import com.likelion.coremodule.cart.domain.Cart;
import com.likelion.coremodule.cart.service.CartQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
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
    private final JwtUtil jwtUtil;

    public List<StoreInfo> findStoreInfo() {

        List<Store> storeList = storeQueryService.findAllStore();
        List<StoreInfo> storeInfoList = new ArrayList<>();

        for (Store store : storeList) {

            List<Menu> menus = menuQueryService.findMenusByStoreId(store.getId());
            List<MenuDetailDTO> menuDetails = menus.stream()
                    .map(menu -> new MenuDetailDTO(menu.getId(),
                            menu.getName(),
                            menu.getPrice(),
                            menu.getContent()))
                    .toList();

            StoreInfo storeInfo = new StoreInfo(
                    store.getId(),
                    store.getName(),
                    store.getReviewCount(),
                    store.getLocation(),
                    store.getOpenHours(),
                    store.getCloseHours(),
                    store.getContact(),
                    menuDetails
            );
            storeInfoList.add(storeInfo);
        }

        return storeInfoList;
    }

    public List<StoreResponse> findStoreByFilter(String search, String category) {

        List<StoreResponse> response = new ArrayList<>();
        List<Store> storeList = storeQueryService.findAllStore();

        if ((search == null || search.isEmpty()) && (category == null || category.isEmpty())) {
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
            StoreResponse ex = new StoreResponse(store.getName(), finalStoreCategory, store.getLocation(), store.getOpenHours());
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
