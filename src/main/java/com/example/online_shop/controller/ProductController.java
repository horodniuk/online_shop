package com.example.online_shop.controller;


import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.dto.responseDto.ProductResponseDto;
import com.example.online_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable("id") Long id){
        return productService.getById(id);
    }




}
