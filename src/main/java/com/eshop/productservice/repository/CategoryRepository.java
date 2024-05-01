package com.eshop.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.productservice.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    boolean existsById(long Id);
    Page<Category> findAll(Pageable pageable);
}