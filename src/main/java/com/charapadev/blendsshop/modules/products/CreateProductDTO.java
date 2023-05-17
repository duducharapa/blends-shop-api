package com.charapadev.blendsshop.modules.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO containing the data to create a new {@link Product}.
 *
 * @param name The product name.
 * @param description The product description.
 * @param price The product price.
 */

public record CreateProductDTO(
   @NotBlank String name,
   @NotBlank String description,
   @NotNull @Positive Double price
) {}
