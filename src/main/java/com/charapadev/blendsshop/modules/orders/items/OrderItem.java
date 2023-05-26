package com.charapadev.blendsshop.modules.orders.items;

import com.charapadev.blendsshop.modules.orders.Order;
import com.charapadev.blendsshop.modules.products.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderitems")
public class OrderItem {

    @Id
    @GeneratedValue
    private Integer id;

    @ToString.Exclude
    @ManyToOne
    private Product product;

    @ToString.Exclude
    @ManyToOne
    private Order order;

    @Column
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderItem orderItem = (OrderItem) o;
        return getId() != null && Objects.equals(getId(), orderItem.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
