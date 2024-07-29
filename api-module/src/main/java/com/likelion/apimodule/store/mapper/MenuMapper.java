package com.likelion.apimodule.store.mapper;

import com.likelion.apimodule.store.dto.MenuDetailDTO;
import com.likelion.coremodule.menu.domain.Menu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuMapper {

    public static MenuDetailDTO toInfoDTO(Menu menu) {
        return new MenuDetailDTO(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getContent(),
                menu.getImageUrl()
        );
    }
}
