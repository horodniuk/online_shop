package com.example.online_shop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable(name = "order_product", joinColumns = {
                                            @JoinColumn(name = "order_id",
                                                        referencedColumnName = "order_id")
                    })
    @MapKeyJoinColumn(name = "product_id", referencedColumnName = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> products;

    public Order() {
        this.products = new HashMap<>();
    }


}
