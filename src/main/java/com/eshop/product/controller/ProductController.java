package com.eshop.product.controller;

import com.eshop.product.model.Product;
import com.eshop.product.reponse.product.ProductCreateResponse;
import com.eshop.product.request.product.ProductCreateRequest;
import com.eshop.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

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
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(ProductCreateResponse.builder().errors(errors).build());
        }

        productService.saveProduct(request);

        return ResponseEntity.ok(ProductCreateResponse.builder()
                .success("Success")
                .build());
    }


    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }
}
