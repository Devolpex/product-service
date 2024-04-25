package com.eshop.product.service;

import com.eshop.product.model.Category;
import com.eshop.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // Example method
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
