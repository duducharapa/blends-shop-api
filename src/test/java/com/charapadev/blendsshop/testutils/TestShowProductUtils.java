package com.charapadev.blendsshop.testutils;

import com.charapadev.blendsshop.modules.products.ShowProductDTO;

public class TestShowProductUtils {

    public static ShowProductDTO generateShowProduct() {
        return ShowProductDTO.builder()
            .id(TestProductUtils.EXPECTED_ID)
            .name(TestProductUtils.EXPECTED_NAME)
            .description(TestProductUtils.EXPECTED_DESCRIPTION)
            .price(TestProductUtils.EXPECTED_PRICE)
            .image(TestProductUtils.EXPECTED_IMAGE)
            .build();
    }

}
