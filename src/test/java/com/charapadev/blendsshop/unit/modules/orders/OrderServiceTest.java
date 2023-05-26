package com.charapadev.blendsshop.unit.modules.orders;

import com.charapadev.blendsshop.modules.orders.*;
import com.charapadev.blendsshop.modules.orders.Order;
import com.charapadev.blendsshop.modules.orders.items.OrderItem;
import com.charapadev.blendsshop.modules.orders.items.OrderItemService;
import com.charapadev.blendsshop.modules.orders.items.ShowOrderItemDTO;
import com.charapadev.blendsshop.modules.products.Product;
import com.charapadev.blendsshop.modules.products.ShowProductDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OrderServiceTest {

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_convert_order_successfully() {
        int EXPECTED_ID = 1;
        int EXPECTED_NUMBER = 1;
        int EXPECTED_QUANTITY = 2;

        Product product = Product.builder()
            .id(1)
            .name("Teste")
            .description("teste descrição")
            .price(2.0)
            .image("teste")
            .build();
        ShowProductDTO expectedProductDTO = ShowProductDTO
            .builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .image(product.getImage())
            .build();
        OrderItem item = OrderItem.builder()
            .id(1)
            .product(product)
            .quantity(EXPECTED_QUANTITY)
            .build();
        ShowOrderItemDTO expectedItemDTO = ShowOrderItemDTO.builder()
            .quantity(item.getQuantity())
            .product(expectedProductDTO)
            .build();

        // Given the order to convert
        Order order = Order.builder()
            .id(1)
            .number(1)
            .build();
        order.addItem(item);
        // And mocked the item service to return the expected value
        Mockito.when(orderItemService.convert(Mockito.any())).thenReturn(expectedItemDTO);

        // When called the CONVERT method
        ShowOrderDTO showDTO = orderService.convert(order);

        // Then the returned DTO must have the same data from original order
        Assertions.assertEquals(EXPECTED_ID, showDTO.id());
        Assertions.assertEquals(EXPECTED_NUMBER, showDTO.number());
        // And the items must have the same product data and quantity
        ShowOrderItemDTO actualItemDTO = showDTO.items().get(0);
        Assertions.assertEquals(EXPECTED_QUANTITY, actualItemDTO.quantity());
        Assertions.assertEquals(expectedProductDTO, actualItemDTO.product());
        // And the item service must be called one time
        Mockito.verify(orderItemService, Mockito.only()).convert(Mockito.any());
    }
}
