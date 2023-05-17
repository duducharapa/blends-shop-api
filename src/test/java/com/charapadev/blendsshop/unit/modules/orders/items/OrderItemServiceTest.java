package com.charapadev.blendsshop.unit.modules.orders.items;

import com.charapadev.blendsshop.exceptions.ProductNotFoundException;
import com.charapadev.blendsshop.modules.orders.Order;
import com.charapadev.blendsshop.modules.orders.items.*;
import com.charapadev.blendsshop.modules.products.Product;
import com.charapadev.blendsshop.modules.products.ProductRepository;
import com.charapadev.blendsshop.modules.products.ProductService;
import com.charapadev.blendsshop.modules.products.ShowProductDTO;
import org.junit.jupiter.api.Assertions;
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
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderItemServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    public void shouldGenerateItem() {
        // Given the data to create an order item
        CreateOrderItemDTO createDTO = new CreateOrderItemDTO(2, 1);
        Product product = Product.builder()
            .id(1).name("Produto").description("Teste").price(2.25)
            .build();
        // And mocked the repository
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When called the generation method
        OrderItem item = orderItemService.generate(createDTO);

        // Then the item must have the same product and quantity from original source
        Assertions.assertEquals(2, item.getQuantity());
        Assertions.assertEquals(product, item.getProduct());
        // And the repository must be called just one time
        Mockito.verify(productRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void shouldThrowErrorItemGeneration() {
        // Given the data to generate an order item
        CreateOrderItemDTO createDTO = new CreateOrderItemDTO(2, 1);

        // When called the generation method
        Executable executable = () -> orderItemService.generate(createDTO);

        // Then must throw the correct exception
        Assertions.assertThrows(ProductNotFoundException.class, executable);
        // And the repository must be called just one time
        Mockito.verify(productRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void shouldConvertItem() {
        // Given an item properly filled
        Product product = Product.builder()
            .id(1).name("Produto").description("Teste").price(2.25)
            .build();
        ShowProductDTO expectProduct = ShowProductDTO.builder()
            .id(1).name("Produto").description("Teste").price(2.25)
            .build();
        OrderItem item = OrderItem.builder()
            .product(product).quantity(2)
            .build();
        // And mocked the product service to returns the expected data
        Mockito.when(productService.convert(Mockito.any())).thenReturn(expectProduct);

        // When called the conversion method
        ShowOrderItemDTO showDTO = orderItemService.convert(item);

        // The the returned DTO must have the same quantity and Product from original source
        Assertions.assertEquals(2, showDTO.quantity());
        Assertions.assertEquals(expectProduct, showDTO.product());
        // And the product service must be called only one time
        Mockito.verify(productService, Mockito.times(1)).convert(Mockito.any());
    }

}
