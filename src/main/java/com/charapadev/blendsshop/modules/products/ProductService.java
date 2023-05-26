package com.charapadev.blendsshop.modules.products;

import com.charapadev.blendsshop.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service used to manipulate the {@link Product} instances.
 */

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StorageService storageService;

    /**
     * Searches all {@link Product Products} instances available.
     *
     * @return The list of Products found.
     */
    public List<Product> list() {
        return productRepository.findAll();
    }

    /**
     * Creates a new {@link Product} on application.
     *
     * @param createDTO The data to create.
     * @return The Product created.
     */
    public Product create(CreateProductDTO createDTO) {
        Product product = Product.builder()
            .name(createDTO.name())
            .description(createDTO.description())
            .price(createDTO.price())
            .build();

        boolean imageNotNull = createDTO.image() != null;
        if (imageNotNull) {
            boolean imageNotBlank = !createDTO.image().isBlank();
            if (imageNotBlank) {
                String imageURL = storageService.uploadFile(createDTO.image());
                product.setImage(imageURL);
            }
        }

        product = productRepository.save(product);
        return product;
    }

    /**
     * Searches a specific {@link Product} by ID.
     *
     * @param productId The product ID.
     * @return The product found.
     * @throws NoSuchElementException If the application cannot find any Product with this ID.
     */
    public Product findOneOrFail(Integer productId) throws NoSuchElementException {
        return productRepository.findById(productId).orElseThrow();
    }

    /**
     * Converts a {@link Product} passed to {@link ShowProductDTO}.
     *
     * @param product The product instance.
     * @return The converted data as DTO.
     */
    public ShowProductDTO convert(Product product) {
        return ShowProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .image(product.getImage())
            .build();
    }

}
