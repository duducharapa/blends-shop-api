package com.charapadev.blendsshop.testutils;

import com.charapadev.blendsshop.modules.products.CreateProductDTO;

public class TestCreateProductUtils {

    public static CreateProductDTO generateCreateProduct(boolean image) {
        return CreateProductDTO.builder()
            .name(TestProductUtils.EXPECTED_NAME)
            .description(TestProductUtils.EXPECTED_DESCRIPTION)
            .price(TestProductUtils.EXPECTED_PRICE)
            .image(image ? "teste123" : null)
            .build();
    }

}
