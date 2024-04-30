package com.eshop.productservice.controller;



import com.eshop.productservice.dto.product.ProductDto;
import com.eshop.productservice.reponse.product.ProductPageResponse;
import com.eshop.productservice.reponse.product.ProductUpdateResponse;
import com.eshop.productservice.request.product.ProductUpdateRequest;
import com.eshop.productservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.eshop.productservice.model.Product;
import com.eshop.productservice.reponse.product.ProductCreateResponse;
import com.eshop.productservice.reponse.product.ProductDeleteResponse;
import com.eshop.productservice.request.product.ProductCreateRequest;
import com.eshop.productservice.service.ProductService;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductCreateResponse> createProduct(@RequestBody @Valid ProductCreateRequest request,
                                                               BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            errors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
        }
        if(productService.productExist(request.getName())){
            errors.add("Product already exists");
        }
        // Check if the category exists
//        if (!categoryService.categoryExists(request.getCategoryId())) {
//            errors.add("Category not found");
//        }
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(ProductCreateResponse.builder().errors(errors).build());
        }

        productService.saveProduct(request);

        return ResponseEntity.ok(ProductCreateResponse.builder()
                .success("Success")
                .build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDeleteResponse> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findProductById(id);
        if (!product.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProductDeleteResponse.builder()
                    .errors(Collections.singletonList("Product not found"))
                    .build());
        }
        if (!productService.deleteProduct(id)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ProductDeleteResponse.builder()
                    .errors(Collections.singletonList("Failed to delete product"))
                    .build());
        }
        return ResponseEntity.ok(ProductDeleteResponse.builder()
                .success("Product deleted successfully")
                .redirectTo("/products")
                .build());
    }

    @GetMapping("/search")

    public ResponseEntity<Page<ProductDto>> searchProducts(
            @RequestParam String search,
            @RequestParam(defaultValue = "1") int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductDto> productDtoPage = productService.searchProducts(search, pageable);
        return ResponseEntity.ok(productDtoPage);
    }

//    @GetMapping("/")
//    public List<Product> getAllProducts() {
//        return productService.findAllProducts();
//    }

    @GetMapping
    public ResponseEntity<ProductPageResponse> getProductByPagination(@RequestParam(defaultValue = "1") int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductDto> productPage = productService.getProductByPagination(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ProductPageResponse.builder()
                .products(productPage.getContent())
                .currentPage(productPage.getNumber() + 1)
                .totalPages(productPage.getTotalPages())
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> updateProduct(@PathVariable Long id,
                                                               @RequestBody @Valid ProductUpdateRequest request, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        Product product = productService.findProductById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProductUpdateResponse.builder()
                    .errors(Collections.singletonList("Product not found")).build());
        }

        if (bindingResult.hasErrors()) {
            errors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
        }

//        if (!productService.categoryExists(request.getCategoryId())) {
//            errors.add("Category not found");
//        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductUpdateResponse.builder().errors(errors).build());
        }

        productService.updateProduct(id, request);
        return ResponseEntity.ok(ProductUpdateResponse.builder()
                .success("Product updated successfully")
                .redirectTo("/products")
                .build());
    }
}


