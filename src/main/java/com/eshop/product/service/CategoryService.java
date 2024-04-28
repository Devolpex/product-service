package com.eshop.product.service;

import com.eshop.product.DTO.CategoryCreateDto;
import com.eshop.product.Exception.CategoryNotFoundException;
import com.eshop.product.model.Category;
import com.eshop.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public void saveCategory(CategoryCreateDto categoryCreateDto){
        // Save the data
        Category category = Category.builder()
                .id((long) categoryCreateDto.getId())
                .name(categoryCreateDto.getName())
                .description(categoryCreateDto.getDescription())
                .created_at(new Date())
                .build();
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
}
