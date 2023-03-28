package com.example.online_shop.service;

import com.example.online_shop.dto.ProductDto;

import java.util.List;

public interface ProductService {
    Long create(ProductDto productDto);
    ProductDto getById(Long id);
    void delete(Long id);
    List<ProductDto> getAll();
}
