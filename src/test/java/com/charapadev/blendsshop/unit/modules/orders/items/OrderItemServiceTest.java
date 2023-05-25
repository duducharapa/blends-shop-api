package com.charapadev.blendsshop.unit.modules.orders.items;

import com.charapadev.blendsshop.exceptions.ProductNotFoundException;
import com.charapadev.blendsshop.modules.orders.items.*;
import com.charapadev.blendsshop.modules.products.Product;
import com.charapadev.blendsshop.modules.products.ProductRepository;
import com.charapadev.blendsshop.modules.products.ProductService;
import com.charapadev.blendsshop.modules.products.ShowProductDTO;
import com.charapadev.blendsshop.testutils.TestOrderItemUtils;
import com.charapadev.blendsshop.testutils.TestProductUtils;
import com.charapadev.blendsshop.testutils.TestShowProductUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderItemServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    public void should_generate_an_order_item_successfully() {
        int validProductID = 1;
        Product expectProduct = TestProductUtils.generateProduct(true);

        // Given the data to create an order item
        CreateOrderItemDTO createDTO = new CreateOrderItemDTO(2, validProductID);
        // And mocked the repository to return the expected product
        Mockito.when(productRepository.findById(validProductID)).thenReturn(Optional.of(expectProduct));

        // When called the GENERATE method
        OrderItem item = orderItemService.generate(createDTO);

        // Then the item must have the same data from original source
        Assertions.assertEquals(TestOrderItemUtils.EXPECTED_QUANTITY, item.getQuantity());
        Assertions.assertEquals(expectProduct, item.getProduct());
        // And the repository must be called one time
        Mockito.verify(productRepository, Mockito.only()).findById(validProductID);
    }

    @Test
    public void should_throw_an_error_on_item_generation() {
        int invalidProductID = 1;

        // Given the data to generate an order item
        CreateOrderItemDTO createDTO = new CreateOrderItemDTO(2, invalidProductID);

        // When called the GENERATE method
        Executable executable = () -> orderItemService.generate(createDTO);

        // Then must throw the expected exception
        Assertions.assertThrows(ProductNotFoundException.class, executable);
        // And the repository must be called one time
        Mockito.verify(productRepository, Mockito.only()).findById(invalidProductID);
    }

    @Test
    public void should_convert_item_successfully() {
        Product product = TestProductUtils.generateProduct(false);
        ShowProductDTO expectProduct = TestShowProductUtils.generateShowProduct();

        // Given an order item
        OrderItem item = TestOrderItemUtils.generateItem(product);
        // And mocked the product service to return the expected data
        Mockito.when(productService.convert(Mockito.any())).thenReturn(expectProduct);

        // When called the CONVERT method
        ShowOrderItemDTO showDTO = orderItemService.convert(item);

        // Then the returned item must have the same data from original
        Assertions.assertEquals(TestOrderItemUtils.EXPECTED_QUANTITY, showDTO.quantity());
        Assertions.assertEquals(expectProduct, showDTO.product());
        // And the product service must be called one time
        Mockito.verify(productService, Mockito.only()).convert(Mockito.any());
    }

}
