package com.eshop.productservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.productservice.Exception.CategoryNotFoundException;
import com.eshop.productservice.model.Category;
import com.eshop.productservice.repository.CategoryRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public void saveCategory(Category category){
        // Save Category data
        categoryRepository.save(category);
    }
    public boolean nameExists(String name){
        return categoryRepository.existsByName(name);
    }
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategoryById(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }

    public Category findCategorytById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        category = Category.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .created_at(category.getCreated_at())
                .build();
        return category;
    }

    public void updateCategory(Long id, Category category) {
        Category updated_category = category;
                updated_category.setUpdate_at(new Date());
        categoryRepository.save(updated_category);
    }
}
