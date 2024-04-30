package com.eshop.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.productservice.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
