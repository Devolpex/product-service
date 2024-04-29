package com.eshop.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.eshop.productservice.DTO.CategoryCreateDto;
import com.eshop.productservice.Exception.CategoryNotFoundException;
import com.eshop.productservice.model.Category;
import com.eshop.productservice.service.CategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3001")
public class CategoryController {
    @Autowired

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "http://localhost:3001")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto, BindingResult bindingResult){
        Map<String, Object> errorsResponse = new HashMap<>();
        List<String> validationsErrors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            validationsErrors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        }
        if (categoryService.nameExists(categoryCreateDto.getName())){
            validationsErrors.add("Category already exists");
        }
        if (!validationsErrors.isEmpty()){
            errorsResponse.put("errors",validationsErrors);
            return ResponseEntity.badRequest().body(errorsResponse);
        }
        categoryService.saveCategory(categoryCreateDto);
        Map<String,String> successMessage = new HashMap<>();
        successMessage.put("success","Category created successfuly");
        return ResponseEntity.ok(successMessage);
    }
    @GetMapping("/")
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        Map<String,String> successMessage = new HashMap<>();
        successMessage.put("Deleted","Category Deleted successfuly");
        return ResponseEntity.ok().build();
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
