package com.eshop.product.repository;

import com.eshop.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsProductByName(String name);
    Product findProductByName(String name);
}
