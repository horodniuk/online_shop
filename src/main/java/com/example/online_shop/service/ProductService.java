package com.example.online_shop.service;

import com.example.online_shop.dto.requestDto.ProductRequestDto;
import com.example.online_shop.entity.Product;

import java.util.List;

public interface ProductService {
    Product create(ProductRequestDto productRequestDto);

    Product getById(Long productId);

    Product delete(Long productId);

    List<Product> getAllProducts();

    Product editProduct(Product product);
}
