package com.charapadev.blendsshop.testutils;

import com.charapadev.blendsshop.modules.orders.items.OrderItem;
import com.charapadev.blendsshop.modules.products.Product;

public class TestOrderItemUtils {

    public static int EXPECTED_ID = 1;
    public static int EXPECTED_QUANTITY = 2;

    public static OrderItem generateItem(Product product) {
        return OrderItem.builder()
            .id(EXPECTED_ID)
            .product(product)
            .order(null)
            .quantity(EXPECTED_QUANTITY)
            .build();
    }

}
