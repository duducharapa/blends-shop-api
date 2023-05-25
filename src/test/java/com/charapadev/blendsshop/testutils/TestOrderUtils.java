package com.charapadev.blendsshop.testutils;

import com.charapadev.blendsshop.modules.orders.Order;
import com.charapadev.blendsshop.modules.orders.items.OrderItem;
import com.charapadev.blendsshop.modules.products.Product;

import java.util.Set;

public class TestOrderUtils {

    public static int EXPECTED_ID = 1;
    public static int EXPECTED_NUMBER = 1;

    public static Order generateOrder() {
        Product product = TestProductUtils.generateProduct(true);
        OrderItem item = TestOrderItemUtils.generateItem(product);

        Order order = Order.builder()
            .id(EXPECTED_ID)
            .number(EXPECTED_NUMBER)
            .build();
        order.addItem(item);

        return order;
    }

}
