package com.likelion.coremodule.menu.service;

import com.likelion.coremodule.menu.domain.Menu;
import com.likelion.coremodule.menu.exception.MenuErrorCode;
import com.likelion.coremodule.menu.exception.MenuException;
import com.likelion.coremodule.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuQueryService {

    private final MenuRepository menuRepository;

    public List<Menu> findAllMenus() {
        return menuRepository.findAll();
    }

    public Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> new MenuException(MenuErrorCode.NO_MENU_INFO));
    }

    public List<Menu> findMenusByStoreId(Long storeId) {
        return menuRepository.findAllByStoreId(storeId);
    }
}
