package com.charapadev.blendsshop.modules.orders;

import com.charapadev.blendsshop.modules.orders.items.ShowOrderItemDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record ShowOrderDTO(
    Integer id,
    Integer number,
    List<ShowOrderItemDTO> items
) {}
