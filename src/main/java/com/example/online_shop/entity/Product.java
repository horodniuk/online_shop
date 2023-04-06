package com.example.online_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;
}
