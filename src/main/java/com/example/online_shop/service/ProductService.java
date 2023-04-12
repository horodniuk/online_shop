package com.example.online_shop.service;

import com.example.online_shop.entity.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);

    Product getById(Long productId);

    Product delete(Long productId);

    List<Product> getAllProducts();

    Product editProduct(Product product);
}
