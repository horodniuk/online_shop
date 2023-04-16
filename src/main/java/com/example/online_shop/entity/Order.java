package com.example.online_shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    @Transient
    @OneToOne
    @JoinColumn(name = "cart_user_id")
    private User userCart;
    @ElementCollection
    @CollectionTable(name = "order_product", joinColumns = {
            @JoinColumn(name = "order_id",
                    referencedColumnName = "order_id")
    })
    @MapKeyJoinColumn(name = "product_id", referencedColumnName = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> products;

    public Order(User user) {
        this.user = user;
        this.products = new HashMap<>();
        this.orderDate = LocalDateTime.now();
    }

    public void addProduct(Product product, int quantity) {
        this.products.put(product, quantity);
    }

    public void addProducts(Map<Product, Integer> products) {
        products.forEach(this::addProduct);
    }

    public double getTotalPrice() {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
    }
}
