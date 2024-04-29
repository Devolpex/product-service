package com.eshop.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.productservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsProductByName(String name);
    Product findProductByName(String name);

}
