package com.example.online_shop.service.impl;

import com.example.online_shop.entity.Product;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.repository.ProductRepository;
import com.example.online_shop.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.save(product);
        return product;
    }

    @Override
    public Product getById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + productId + " not found!"));
    }

    @Transactional
    @Override
    public Product delete(Long productId) {
        Product product = getById(productId);
        productRepository.delete(product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    @Override
    public Product editProduct(Product product) {
        Product productToEdit = getById(product.getProductId());
        productToEdit.setName(product.getName());
        productToEdit.setPrice(product.getPrice());
        return productToEdit;
    }
}
