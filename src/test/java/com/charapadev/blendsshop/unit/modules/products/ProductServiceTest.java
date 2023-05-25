package com.charapadev.blendsshop.unit.modules.products;

import com.charapadev.blendsshop.modules.products.*;
import com.charapadev.blendsshop.storage.StorageService;
import com.charapadev.blendsshop.testutils.TestCreateProductUtils;
import com.charapadev.blendsshop.testutils.TestProductUtils;
import org.junit.jupiter.api.*;
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
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_list_products_successfully() {
        int EXPECTED_LIST_SIZE = 2;

        // Given a list of products
        List<Product> expected = List.of(
            TestProductUtils.generateProduct(true),
            TestProductUtils.generateOtherProduct()
        );
        // And mocked the repository to return these products
        Mockito.when(productRepository.findAll()).thenReturn(expected);

        // When called the LIST method
        List<Product> products = productService.list();

        // Then the product list size must be exactly as expected
        Assertions.assertEquals(EXPECTED_LIST_SIZE, products.size());
        // And the repository must be called one time
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void should_create_product_successfully() {
        Product expectedProduct = TestProductUtils.generateProduct(true);

        // Given the data necessary to create a product
        CreateProductDTO createDTO = TestCreateProductUtils.generateCreateProduct(true);
        // And mocked the storage service to return the image URL
        Mockito.when(storageService.uploadFile(Mockito.any())).thenReturn(TestProductUtils.EXPECTED_IMAGE);
        // And mocked the repository to return the expected product
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(expectedProduct);

        // When called the CREATE method
        Product product = productService.create(createDTO);

        // Then the product must have the same properties was passed on creation
        Assertions.assertEquals(TestProductUtils.EXPECTED_NAME, product.getName());
        Assertions.assertEquals(TestProductUtils.EXPECTED_DESCRIPTION, product.getDescription());
        Assertions.assertEquals(TestProductUtils.EXPECTED_PRICE, product.getPrice());
        Assertions.assertEquals(TestProductUtils.EXPECTED_IMAGE, product.getImage());
        // And the repository must be called one time
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
        // And the storage service must be called one time
        Mockito.verify(storageService, Mockito.times(1)).uploadFile(Mockito.any());
    }

    @Test
    public void should_create_product_successfully_without_call_storage() {
        Product expectedProduct = TestProductUtils.generateProduct(false);

        // Given the data necessary to create a product
        CreateProductDTO createDTO = TestCreateProductUtils.generateCreateProduct(false);
        // And mocked the repository to return the expected product
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(expectedProduct);

        // When called the CREATE method
        Product product = productService.create(createDTO);

        // Then the returned product must be equals as expect
        Assertions.assertEquals(TestProductUtils.EXPECTED_NAME, product.getName());
        Assertions.assertEquals(TestProductUtils.EXPECTED_DESCRIPTION, product.getDescription());
        Assertions.assertEquals(TestProductUtils.EXPECTED_PRICE, product.getPrice());
        Assertions.assertEquals("", product.getImage());
        // And the repository must be called one time
        Mockito.verify(productRepository, Mockito.only()).save(Mockito.any());
        // And the storage service must be never called
        Mockito.verify(storageService, Mockito.never()).uploadFile(Mockito.any());
    }

    @Test
    public void should_search_product_successfully() {
        Product expectedProduct = TestProductUtils.generateProduct(true);

        // Given an ID from a valid Product
        Integer validID = 1;
        // And mocked the repository to return that Product
        Mockito.when(productRepository.findById(validID)).thenReturn(Optional.of(expectedProduct));

        // When called the SEARCH method
        Product product = productService.findOneOrFail(validID);

        // Then the returned object must be equals as expected
        Assertions.assertEquals(expectedProduct, product);
        // And the repository must be called one time
        Mockito.verify(productRepository, Mockito.times(1)).findById(validID);
    }

    @Test
    public void should_throw_an_error_on_search_product() {
        // Given the ID of an invalid Product
        Integer invalidID = 1;

        // When called the SEARCH method
        Executable executable = () -> productService.findOneOrFail(invalidID);

        // Then must throw the expected exception
        Assertions.assertThrows(NoSuchElementException.class, executable);
        // And the repository must be called one time
        Mockito.verify(productRepository, Mockito.times(1)).findById(invalidID);
    }

    @Test
    public void should_convert_product_successfully() {
        // Given the product with the data properly filled
        Product product = TestProductUtils.generateProduct(true);

        // When called the convert method
        ShowProductDTO showDTO = productService.convert(product);

        // Then the returned DTO must have the same data from product
        Assertions.assertEquals(TestProductUtils.EXPECTED_ID, showDTO.id());
        Assertions.assertEquals(TestProductUtils.EXPECTED_NAME, showDTO.name());
        Assertions.assertEquals(TestProductUtils.EXPECTED_DESCRIPTION, showDTO.description());
        Assertions.assertEquals(TestProductUtils.EXPECTED_PRICE, showDTO.price());
        Assertions.assertEquals(TestProductUtils.EXPECTED_IMAGE, showDTO.image());
    }
}
