package com.example.online_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

    @Data
    @Entity
    @Table(name = "user_cart")
    public class UserCart {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "cart_id")
        private Long cartId;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")
        private User user;

        @OneToMany(mappedBy = "userCart")
        private List<Product> products;

        @OneToMany(mappedBy = "userCart")
        private List<Order> orders;
    }
