package com.example.online_shop.controller;


import com.example.online_shop.dto.requestDto.ProductRequestDto;
import com.example.online_shop.entity.Product;
import com.example.online_shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productService.getById(id);
    }

    @PostMapping
    public Product addProduct(@Valid ProductRequestDto productRequestDto) {
        return productService.create(productRequestDto);
    }

    @PutMapping
    public Product editProduct(@Valid ProductRequestDto productRequestDto, Long productId) {
        return productService.editProduct(productRequestDto, productId);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable("id") Long productId) {
        return productService.delete(productId);
    }
}
