package com.eshop.productservice.controller;

import com.eshop.productservice.Response.product.ProductHomeResponse;
import com.eshop.productservice.Response.product.ProductPageResponse;
import com.eshop.productservice.dto.category.CategoryDTO;
import com.eshop.productservice.dto.product.ProductDto;
import com.eshop.productservice.dto.product.ProductHomeDto;
import com.eshop.productservice.enums.NotificationStatus;
import com.eshop.productservice.file.FileService;
import com.eshop.productservice.file.GoogleDriveService;
import com.eshop.productservice.request.product.ProductUpdateRequest;
import com.eshop.productservice.response.product.ProductCreateResponse;
import com.eshop.productservice.response.product.ProductDeleteResponse;

import com.eshop.productservice.response.product.ProductResponse;
import com.eshop.productservice.response.product.ProductUpdateResponse;
import com.eshop.productservice.response.settings.Notification;
import com.eshop.productservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.eshop.productservice.model.Category;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.request.product.ProductCreateRequest;
import com.eshop.productservice.service.ProductService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final GoogleDriveService googleDriveService;

    // Search products by name
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(
            @RequestParam String search,
            @RequestParam(defaultValue = "1") int page) {
        int size = 5;
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductDto> productDtoPage = productService.searchProducts(search, pageable);
        return ResponseEntity.ok(productDtoPage);
    }

    // Get all products by pagination (default function)
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

    //Get last 6 Product HomePage
    @GetMapping("/Last6")
    public Page<ProductHomeDto> getLast6Products() {
        return productService.getLast6Product();
    }

    @GetMapping("/Home")
    public ResponseEntity<ProductHomeResponse> getProductHomePagination(@RequestParam(defaultValue = "1") int page) {
        int size = 6;
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductHomeDto> productPage = productService.getLastAllProduct(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ProductHomeResponse.builder()
                .products(productPage.getContent())
                .currentPage(productPage.getNumber() + 1)
                .totalPages(productPage.getTotalPages())
                .build());
    }



    // Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {

        Notification notification = new Notification();
        ProductDto productDto = productService.getProductById(id);

        if (productDto == null) {
            notification.setMessage("Product not found");
            notification.setStatus(NotificationStatus.ERROR);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProductResponse.builder()
                    .notification(notification)
                    .build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(ProductResponse.builder()
                .product(productDto)
                .build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductCreateResponse> createProduct(@RequestBody @Valid ProductCreateRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductCreateResponse.builder().errors(errors).build());
        }

        List<String> errors = new ArrayList<>();

        if (productService.productExist(request.getName())) {
            errors.add("Product already exists");
        }

        if (!categoryService.categoryExists(request.getCategoryId())) {
            errors.add("Category not found");
        }

        if (!fileService.checkIfImage(request.getImage())) {
            errors.add("Invalid image file");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductCreateResponse.builder().errors(errors).build());
        }

        try {
            File imageProduct = fileService.convertToImage(request.getImage(), request.getName());
            String imageUrl = googleDriveService.uploadImageToDrive(imageProduct, getImageType(request.getImage()));
            request.setImage(imageUrl);
            productService.saveProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(ProductCreateResponse.builder()
                    .success("Product created successfully")
                    .redirectTo("/products")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ProductCreateResponse.builder()
                    .errors(Collections.singletonList("Failed to create product check try again..!")).build());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> updateProduct(@PathVariable Long id,
            @RequestBody @Valid ProductUpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductUpdateResponse.builder().errors(errors).build());
        }

        List<String> errors = new ArrayList<>();
        Product product = productService.findProductById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProductUpdateResponse.builder()
                    .errors(Collections.singletonList("Product not found")).build());
        }

        if (!categoryService.categoryExists(request.getCategoryId())) {
            errors.add("Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProductUpdateResponse.builder()
                    .errors(Collections.singletonList("Category not found")).build());
        }

        if (this.isValidImageUrl(request.getImage())) {
            productService.updateProduct(id, request);
            return ResponseEntity.ok(ProductUpdateResponse.builder()
            .success("Product updated successfully")
            .redirectTo("/products")
            .build());
        } else {
            if (!fileService.checkIfImage(request.getImage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProductUpdateResponse.builder()
                        .errors(Collections.singletonList("Invalid image file")).build());
            }

            try {
                File imageProduct = fileService.convertToImage(request.getImage(), request.getName());
                String imageUrl = googleDriveService.uploadImageToDrive(imageProduct,
                        getImageType(request.getImage()));
                request.setImage(imageUrl);
                productService.updateProduct(id, request);
                return ResponseEntity.status(HttpStatus.OK).body(ProductUpdateResponse.builder()
                        .success("Product updated successfully")
                        .redirectTo("/products")
                        .build());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ProductUpdateResponse.builder()
                        .errors(Collections.singletonList("Failed to create product check try again..!")).build());
            }
        }


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

    private String getImageType(String base64Image) {
        String[] parts = base64Image.split(",");
        String mimeType = parts[0].split(":")[1].split(";")[0];
        return mimeType;
    }

    private static boolean isValidImageUrl(String imageUrl) {
        // Regular expression pattern to match "https://drive.google.com/thumbnail?id="
        // followed by any characters
        String regex = "^https:\\/\\/drive\\.google\\.com\\/thumbnail\\?id=.*$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(imageUrl);

        // Check if the image URL matches the pattern
        return matcher.matches();
    }
}
