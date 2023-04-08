package com.example.online_shop.service.impl;

import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.dto.requestDto.ProductRequestDto;
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
    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Product product = modelMapper.map(productRequestDto, Product.class);
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getById(Long productId) {
        Product product = getProduct(productId);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + productId + " not found!"));
    }


    @Override
    public ProductResponseDto delete(Long productId) {
        Product product = getProduct(productId);
        productRepository.delete(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        var users = (List<Product>) productRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto editProduct(ProductRequestDto productRequestDto, Long productId) {
        Product product = getProduct(productId);
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        return modelMapper.map(product, ProductResponseDto.class);
    }
}
