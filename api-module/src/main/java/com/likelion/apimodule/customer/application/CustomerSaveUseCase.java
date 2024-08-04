package com.likelion.apimodule.customer.application;

import com.likelion.apimodule.customer.dto.AddMenu;
import com.likelion.commonmodule.image.service.AwsS3Service;
import com.likelion.coremodule.VisitList.domain.VisitList;
import com.likelion.coremodule.VisitList.service.VisitListQueryService;
import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.service.MenuQueryService;
import com.likelion.coremodule.order.domain.Order;
import com.likelion.coremodule.order.service.OrderQueryService;
import com.likelion.coremodule.store.domain.Store;
import com.likelion.coremodule.store.service.StoreQueryService;
import com.likelion.coremodule.user.application.UserQueryService;
import com.likelion.coremodule.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerSaveUseCase {

    private final VisitListQueryService visitListQueryService;
    private final OrderQueryService orderQueryService;
    private final UserQueryService userQueryService;
    private final StoreQueryService storeQueryService;
    private final AwsS3Service awsS3Service;
    private final MenuQueryService menuQueryService;

    public void changeToPreparing(Long storeId, Long orderId) {

        Order order = orderQueryService.findOrderById(orderId);
        User user = userQueryService.findById(order.getUser().getUserId());

        VisitList visitList = visitListQueryService.findVisitListByStoreIdAndUserId(storeId, user.getUserId());
        visitList.updateToPreparing();
    }

    public void changeToPrepared(Long storeId, Long orderId) {

        Order order = orderQueryService.findOrderById(orderId);
        User user = userQueryService.findById(order.getUser().getUserId());

        VisitList visitList = visitListQueryService.findVisitListByStoreIdAndUserId(storeId, user.getUserId());
        visitList.updateToPrepared();
    }

    public void addMenuInfo(Long storeId, AddMenu addMenu, MultipartFile multipartFile) {

        Store store = storeQueryService.findStoreById(storeId);
        String imageUrl = awsS3Service.uploadFile(multipartFile);

        final Menu menu = Menu.builder().store(store).name(addMenu.name()).content(addMenu.content()).
                imageUrl(imageUrl).price(addMenu.price()).build();
        menuQueryService.saveMenu(menu);
    }
}
