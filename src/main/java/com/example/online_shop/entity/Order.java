package com.example.online_shop.entity;

import com.example.online_shop.service.ProductService;
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

    @ElementCollection
    @CollectionTable(name = "order_product", joinColumns = {
            @JoinColumn(name = "order_id",
                    referencedColumnName = "order_id")
    })
    @MapKeyJoinColumn(name = "product_id", referencedColumnName = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> products = new HashMap<>();

    public Order(User user) {
        this.user = user;
        this.orderDate = LocalDateTime.now();
    }

    public void addProducts(Map<Long, Integer> products, ProductService productService) {
        Map<Product, Integer> productsTemp = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Product product = productService.getById(entry.getKey());
            productsTemp.put(product, entry.getValue());
        }
        this.products.putAll(productsTemp);
    }

    public void removeProducts(Product product, int quantity) {
        products.compute(product, (p, q) -> {
            int newQuantity = q - quantity;
            if (newQuantity <= 0) {
                return null;
            } else {
                return newQuantity;
            }
        });
    }

    public double getTotalPrice() {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
    }
}
