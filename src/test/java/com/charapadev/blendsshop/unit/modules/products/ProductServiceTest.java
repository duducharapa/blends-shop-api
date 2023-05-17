package com.charapadev.blendsshop.unit.modules.products;

import com.charapadev.blendsshop.modules.products.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
// TODO: Search how to resolve it
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldListProducts() {
        // Given the list of products
        List<Product> expected = List.of(
            new Product(1, "primeiro produto", "primeiro", 3.25, null),
            new Product(2, "segundo produto", "esse vem depois", 2.00, null)
        );
        // And mocked the repository to return these products
        Mockito.when(productRepository.findAll()).thenReturn(expected);

        // When called the list method
        List<Product> products = productService.list();

        // Then the size of products list should maintain in value 2
        Assertions.assertEquals(2, products.size());
        // And the repository must be called just one time
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void shouldCreateProduct() {
        // Given the data necessary to create a product
        CreateProductDTO createDTO = new CreateProductDTO("novo produto", "sobre o produto", 2.25);

        // When called the create method
        Product product = productService.create(createDTO);

        // Then the product must have the same properties was passed on creation
        // A name, description and price
        Assertions.assertEquals("novo produto", product.getName());
        Assertions.assertEquals("sobre o produto", product.getDescription());
        Assertions.assertEquals(2.25, product.getPrice());
        // And the repository must be called just one time
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    public void shouldSearchProductCorrectly() {
        // Given an identifier of a Product that exists
        Integer validID = 1;
        // And mocked a Product with this ID in repository
        Product expected = new Product(1, "primeiro produto", "primeiro", 3.25, null);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(expected));

        // When called the search method
        Product product = productService.findOneOrFail(validID);

        // Then the returned value must be equals to product found on repository
        Assertions.assertEquals(expected, product);
        // And the repository must be called just one time
        Mockito.verify(productRepository, Mockito.times(1)).findById(validID);
    }

    @Test
    public void shouldThrowErrorOnSearch() {
        // Given the identifier of a Product does not exist
        Integer invalidID = 1;

        // When called the search method
        Executable executable = () -> productService.findOneOrFail(invalidID);

        // Then must throw the correct exception
        Assertions.assertThrows(NoSuchElementException.class, executable);
        // And the repository must be called just one time
        Mockito.verify(productRepository, Mockito.times(1)).findById(invalidID);
    }

    @Test
    public void shouldConvertProduct() {
        // Given the product with the data properly filled
        Product product = new Product(1, "Primeiro produto", "Descrição", 2.25, null);

        // When called the convert method
        ShowProductDTO showDTO = productService.convert(product);

        // Then the returned DTO must have the same data from product
        Assertions.assertEquals(1, showDTO.id());
        Assertions.assertEquals("Primeiro produto", showDTO.name());
        Assertions.assertEquals("Descrição", showDTO.description());
        Assertions.assertEquals(2.25, showDTO.price());
    }
}
