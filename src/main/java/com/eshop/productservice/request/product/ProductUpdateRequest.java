package com.eshop.productservice.request.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
    @NotBlank(message = "Product Name cannot be empty")
    @Pattern(regexp = "[a-zA-Z]+[0-9]*", message = "Product name must contain alphabetic characters and optionally numbers")
    private String name;

    // Description should not be empty
    @NotBlank(message = "Description cannot be empty")
    private String description;

    // Price should be a positive double
    @Positive(message = "Price must be positive")
    private double price;
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;  // Default value set to 1, validation ensures it's 1 or more
    // Image should be a string but there's no need for specific validation unless you require something specific like format checking
    private String image;
    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
