package com.likelion.apimodule.store.dto;

public record MenuDetailDTO(Long marketId,
                            String name,
                            Integer price,
                            String content,
                            String menuImage) {
}
