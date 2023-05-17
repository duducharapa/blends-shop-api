package com.charapadev.blendsshop.modules.orders.items;

import com.charapadev.blendsshop.modules.orders.items.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}