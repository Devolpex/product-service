package com.eshop.productservice.controller;

import com.eshop.productservice.dto.product.ProductDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.eshop.productservice.Exception.CategoryNotFoundException;
import com.eshop.productservice.request.category.*;
import com.eshop.productservice.response.product.ProductPageResponse;
import com.eshop.productservice.Response.category.*;
import com.eshop.productservice.model.Category;
import com.eshop.productservice.service.CategoryService;

import java.util.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    @Autowired

    private final CategoryService categoryService;
    

    @GetMapping
    public ResponseEntity<CategoryPageResponse> getCategoryByPagination(@RequestParam(defaultValue = "1") int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CategoryPageRequest> categoryPage = categoryService.getCategoryByPagination(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(CategoryPageResponse.builder()
                .category(categoryPage.getContent())
                .currentPage(categoryPage.getNumber() + 1)
                .totalPages(categoryPage.getTotalPages())
                .build());
    }


    @GetMapping("/{id}")
    public  ResponseEntity<?> getCategoryById(@PathVariable long id){
        Category category = categoryService.findCategorytById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CategoryUpdateResponse.builder()
                    .errors(Collections.singletonList("Category not found")).build());
        }
        return ResponseEntity.ok(category);
    }


    @GetMapping("/all-categories")
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryCreateResponse> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest, BindingResult bindingResult){

        List<String> validationsErrors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            validationsErrors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(CategoryCreateResponse.builder().errors(validationsErrors).build());
        }
        if (categoryService.nameExists(categoryCreateRequest.getName())){
            validationsErrors.add("Category already exists");
        }
        if (!validationsErrors.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CategoryCreateResponse.builder().errors(validationsErrors).build());
        }
        Category category = Category.builder()
                .name(categoryCreateRequest.getName())
                .description(categoryCreateRequest.getDescription())
                .created_at(new Date())
                .build();
        categoryService.saveCategory(category);
        return ResponseEntity.ok(CategoryCreateResponse.builder()
                .success("Category created successfully")
                .redirectTo("/categories")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryUpdateResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest, BindingResult bindingResult){

        List<String> validationsErrors = new ArrayList<>();

        Category category = categoryService.findCategorytById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CategoryUpdateResponse.builder()
                    .errors(Collections.singletonList("Category not found")).build());
        }
        if (bindingResult.hasErrors()) {
            validationsErrors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        }
        if (!validationsErrors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CategoryUpdateResponse.builder().errors(validationsErrors).build());
        }

        category.setName(categoryUpdateRequest.getName());
        category.setDescription(categoryUpdateRequest.getDescription());
        categoryService.updateCategory(id, category);
        return ResponseEntity.ok(CategoryUpdateResponse.builder()
                .success("Category updated successfully")
                .redirectTo("/categories")
                .build());
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteCategory(@PathVariable long id){

        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(CategoryUpdateResponse.builder()
                .success("Category deleted successfully")
                .redirectTo("/categories")
                .build());
    }
    //Handle the Custom Exception if id Non-Existent
    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(CategoryNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public String handleCategoryNotFoundException(CategoryNotFoundException ex) {
            return ex.getMessage();
        }
    }

}
