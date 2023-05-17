package com.charapadev.blendsshop.modules.orders.items;

import com.charapadev.blendsshop.exceptions.ProductNotFoundException;
import com.charapadev.blendsshop.modules.orders.Order;
import com.charapadev.blendsshop.modules.products.Product;
import com.charapadev.blendsshop.modules.products.ProductRepository;
import com.charapadev.blendsshop.modules.products.ProductService;
import com.charapadev.blendsshop.modules.products.ShowProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem generate(CreateOrderItemDTO createDTO) throws ProductNotFoundException {
        Product product = productRepository.findById(createDTO.productID())
            .orElseThrow(ProductNotFoundException::new);

        return OrderItem.builder()
            .product(product)
            .quantity(createDTO.quantity())
            .build();
    }

    public void create(OrderItem item, Order order) {
        item.setOrder(order);

        orderItemRepository.save(item);
    }

    public ShowOrderItemDTO convert(OrderItem item) {
        ShowProductDTO productDTO = productService.convert(item.getProduct());

        return ShowOrderItemDTO.builder()
            .product(productDTO)
            .quantity(item.getQuantity())
            .build();
    }

}
