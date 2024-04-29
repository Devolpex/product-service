package com.eshop.productservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.productservice.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsProductByName(String name);
    Product findProductByName(String name);


    @Query("SELECT p FROM Product p JOIN p.category c WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> findByProductNameOrCategoryName(@Param("search") String search, Pageable pageable);


}
