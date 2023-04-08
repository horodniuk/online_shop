package com.example.online_shop.service;

import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.dto.requestDto.ProductRequestDto;
import com.example.online_shop.dto.responseDto.ProductResponseDto;
import com.example.online_shop.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponseDto create(ProductRequestDto productRequestDto);

    ProductResponseDto getById(Long productId);

    Product getProduct(Long productId);

    ProductResponseDto delete(Long productId);

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto editProduct(ProductRequestDto productRequestDto, Long productId);
}
