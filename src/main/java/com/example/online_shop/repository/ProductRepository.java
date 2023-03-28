package com.example.online_shop.repository;

import com.example.online_shop.entity.Product;
import org.springframework.data.repository.CrudRepository;



public interface ProductRepository extends CrudRepository<Product, Long> {

}
