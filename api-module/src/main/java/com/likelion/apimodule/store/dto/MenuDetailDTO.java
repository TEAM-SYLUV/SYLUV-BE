package com.likelion.apimodule.store.dto;

public record MenuDetailDTO(Long menuId,
                            String name,
                            Integer price,
                            String content,
                            String menuImage) {
}
