package com.example.online_shop.service.impl;

import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.dto.responseDto.ProductResponseDto;
import com.example.online_shop.entity.Product;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.repository.ProductRepository;
import com.example.online_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long create(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return productRepository.save(product).getProductId();
    }

    @Override
    public ProductResponseDto getById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + productId + " not found!"));
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public Product getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + productId + " not found!"));
        return product;
    }


    @Override
    public ProductResponseDto delete(Long productId) {

        productRepository.deleteById(productId);
        return null;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        var users = (List<Product>) productRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, ProductResponseDto.class))
                .collect(Collectors.toList());
    }
}
