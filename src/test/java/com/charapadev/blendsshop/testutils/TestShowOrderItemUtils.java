package com.charapadev.blendsshop.testutils;

import com.charapadev.blendsshop.modules.orders.items.ShowOrderItemDTO;

public class TestShowOrderItemUtils {

    public static ShowOrderItemDTO generateShowItem() {
        return ShowOrderItemDTO.builder()
            .product(TestShowProductUtils.generateShowProduct())
            .quantity(TestOrderItemUtils.EXPECTED_QUANTITY)
            .build();
    }

}
