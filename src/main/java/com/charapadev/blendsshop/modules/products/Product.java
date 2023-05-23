package com.charapadev.blendsshop.modules.products;

import com.charapadev.blendsshop.modules.orders.items.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String name;

    @Column
    private String description = "";

    @Column
    private Double price;

    @Column
    private String image;

    @ToString.Exclude
    @OneToMany(mappedBy = "product")
    private Set<OrderItem> items = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Sets an empty string if {@link #image image} property is null before persist on database.
     */
    @PrePersist
    private void setEmptyImage() {
        if (image == null) image = "";
    }
}
