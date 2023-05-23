package com.charapadev.blendsshop.unit.modules.orders;

import com.charapadev.blendsshop.modules.orders.*;
import com.charapadev.blendsshop.modules.orders.items.OrderItem;
import com.charapadev.blendsshop.modules.orders.items.OrderItemService;
import com.charapadev.blendsshop.modules.orders.items.ShowOrderItemDTO;
import com.charapadev.blendsshop.modules.products.Product;
import com.charapadev.blendsshop.modules.products.ShowProductDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldConvertOrder() {
        // Given a Product to order
        Product product = new Product(1, "Produto 1", "Produto pedido", 2.25, "", null);
        ShowProductDTO expectedProductDTO = ShowProductDTO.builder()
            .id(product.getId()).name(product.getName()).description(product.getDescription()).price(product.getPrice())
            .build();
        ShowOrderItemDTO expectedItemDTO = ShowOrderItemDTO.builder()
            .product(expectedProductDTO).quantity(2)
            .build();
        // And the items to order
        Set<OrderItem> items = Set.of(
            OrderItem.builder().id(1).product(product).quantity(2).build()
        );
        // And the order itself
        Order order = Order.builder()
            .id(1).number(1).items(items)
            .build();
        // And mocked the item service to return the expected value
        Mockito.when(orderItemService.convert(Mockito.any())).thenReturn(expectedItemDTO);

        // When called the convert method
        ShowOrderDTO showDTO = orderService.convert(order);

        // Then the returned DTO must have the same ID and order number from the original order
        Assertions.assertEquals(1, showDTO.id());
        Assertions.assertEquals(1, showDTO.number());
        // And the items must have the same product data and quantity
        ShowOrderItemDTO actualItemDTO = showDTO.items().get(0);
        Assertions.assertEquals(2, actualItemDTO.quantity());
        Assertions.assertEquals(expectedProductDTO, actualItemDTO.product());
        // And the item service should be called just one time
        Mockito.verify(orderItemService, Mockito.times(1)).convert(Mockito.any());
    }
}
