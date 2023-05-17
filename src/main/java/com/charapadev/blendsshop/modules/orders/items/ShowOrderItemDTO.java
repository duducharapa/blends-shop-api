package com.charapadev.blendsshop.modules.orders.items;

import com.charapadev.blendsshop.modules.products.ShowProductDTO;
import lombok.Builder;

@Builder
public record ShowOrderItemDTO(
    ShowProductDTO product,
    Integer quantity
) {
}
