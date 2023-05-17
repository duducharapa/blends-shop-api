package com.charapadev.blendsshop.modules.orders;

import com.charapadev.blendsshop.modules.orders.items.CreateOrderItemDTO;
import jakarta.validation.Valid;

import java.util.List;

public record CreateOrderDTO(
    @Valid List<CreateOrderItemDTO> items
) {}
