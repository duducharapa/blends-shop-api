package com.charapadev.blendsshop.testutils;

import com.charapadev.blendsshop.modules.products.Product;

import java.util.Set;

public class TestProductUtils {

    public static int EXPECTED_ID = 1;
    public static String EXPECTED_NAME = "Primeiro produto";
    public static String EXPECTED_DESCRIPTION = "Este é o primeiro produto";
    public static String EXPECTED_IMAGE = "https://testeimagem.com.br/imagemteste.jpeg";
    public static double EXPECTED_PRICE = 2.25;

    public static Product generateProduct(boolean image) {
        return Product.builder()
            .id(EXPECTED_ID)
            .name(EXPECTED_NAME)
            .description(EXPECTED_DESCRIPTION)
            .image(image ? EXPECTED_IMAGE : "")
            .price(EXPECTED_PRICE)
            .items(Set.of())
            .build();
    }

    public static Product generateOtherProduct() {
        return Product.builder()
            .id(2)
            .name("Segundo produto")
            .description("Esse é outro produto")
            .image("")
            .price(1.50)
            .items(Set.of())
            .build();
    }

}
