package com.example.online_shop.service.impl;

import com.example.online_shop.dto.ProductDto;
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
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + id + " not found!"));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> getAll() {
        var users = (List<Product>) productRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, ProductDto.class))
                .collect(Collectors.toList());
    }
}
