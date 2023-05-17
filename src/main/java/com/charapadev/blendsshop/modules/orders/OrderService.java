package com.charapadev.blendsshop.modules.orders;

import com.charapadev.blendsshop.exceptions.ProductNotFoundException;
import com.charapadev.blendsshop.exceptions.SearchProductsException;
import com.charapadev.blendsshop.modules.orders.items.OrderItem;
import com.charapadev.blendsshop.modules.orders.items.OrderItemService;
import com.charapadev.blendsshop.modules.orders.items.ShowOrderItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    private static Integer QUEUE = 0;

    @Transactional
    public Order create(CreateOrderDTO createDTO) throws SearchProductsException {
        List<OrderItem> items;

        try {
            items = createDTO.items().stream()
                .map(orderItemService::generate)
                .toList();
        } catch (ProductNotFoundException ex) {
            throw new SearchProductsException();
        }

        Integer queuePosition = addOnQueue();
        Order order = Order.builder()
            .number(queuePosition)
            .build();
        order = orderRepository.save(order);

        createItems(items, order);
        order.setItems(new HashSet<>(items));
        order = orderRepository.save(order);

        return order;
    }

    private void createItems(List<OrderItem> items, Order order) {
        items.forEach(item -> orderItemService.create(item, order));
    }

    private Integer addOnQueue() {
        QUEUE++;
        return QUEUE;
    }

    public ShowOrderDTO convert(Order order) {
        List<ShowOrderItemDTO> itemDTOs = order.getItems().stream()
            .map(orderItemService::convert)
            .toList();

        return ShowOrderDTO.builder()
            .id(order.getId())
            .items(itemDTOs)
            .number(order.getNumber())
            .build();
    }

}
