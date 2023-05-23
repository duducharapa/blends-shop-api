package com.charapadev.blendsshop.modules.products;

import lombok.Builder;

/**
 * DTO containing the data relevant about a {@link Product} to be shown on response.
 *
 * @param id The product ID.
 * @param name The product name.
 * @param description The product description.
 * @param price The product price.
 * @param image The product image URL.
 */

@Builder
public record ShowProductDTO(
   Integer id,
   String name,
   String description,
   Double price,
   String image
) {}
