package com.charapadev.blendsshop.modules.orders.items;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderItemDTO(
    Integer quantity,

    @JsonProperty("product_id")
    Integer productID
) {
}
