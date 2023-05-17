package com.charapadev.blendsshop.modules.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository layer for {@link Product} entity.
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}