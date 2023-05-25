package com.charapadev.blendsshop.unit.modules.orders;

import com.charapadev.blendsshop.modules.orders.*;
import com.charapadev.blendsshop.modules.orders.Order;
import com.charapadev.blendsshop.modules.orders.items.OrderItem;
import com.charapadev.blendsshop.modules.orders.items.OrderItemService;
import com.charapadev.blendsshop.modules.orders.items.ShowOrderItemDTO;
import com.charapadev.blendsshop.modules.products.Product;
import com.charapadev.blendsshop.modules.products.ShowProductDTO;
import com.charapadev.blendsshop.testutils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashSet;
import java.util.Set;

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
        ShowProductDTO expectedProductDTO = TestShowProductUtils.generateShowProduct();
        ShowOrderItemDTO expectedItemDTO = TestShowOrderItemUtils.generateShowItem();
        // Given the order to convert
        Order order = TestOrderUtils.generateOrder();
        // And mocked the item service to return the expected value
        Mockito.when(orderItemService.convert(Mockito.any())).thenReturn(expectedItemDTO);

        // When called the CONVERT method
        ShowOrderDTO showDTO = orderService.convert(order);

        // Then the returned DTO must have the same data from original order
        Assertions.assertEquals(TestOrderUtils.EXPECTED_ID, showDTO.id());
        Assertions.assertEquals(TestOrderUtils.EXPECTED_NUMBER, showDTO.number());
        // And the items must have the same product data and quantity
        ShowOrderItemDTO actualItemDTO = showDTO.items().get(0);
        Assertions.assertEquals(TestOrderItemUtils.EXPECTED_QUANTITY, actualItemDTO.quantity());
        Assertions.assertEquals(expectedProductDTO, actualItemDTO.product());
        // And the item service must be called one time
        Mockito.verify(orderItemService, Mockito.only()).convert(Mockito.any());
    }
}
