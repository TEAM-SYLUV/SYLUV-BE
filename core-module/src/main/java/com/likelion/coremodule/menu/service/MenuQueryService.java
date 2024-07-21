package com.likelion.coremodule.menu.service;

import com.likelion.coremodule.menu.domain.Menu;
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

    public List<Menu> findMenusByStoreId(Long storeId) {

        return menuRepository.findAllByStoreId(storeId);
    }
}
