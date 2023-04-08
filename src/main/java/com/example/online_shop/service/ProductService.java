package com.example.online_shop.service;

import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.dto.responseDto.ProductResponseDto;
import com.example.online_shop.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponseDto create(ProductDto productDto);
    ProductResponseDto getById(Long productId);
    Product getProduct(Long productId);
    ProductResponseDto delete(Long productId);
    List<ProductResponseDto> getAllProducts();
}
