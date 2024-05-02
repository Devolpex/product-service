package com.eshop.productservice.request.product;

import io.micrometer.common.lang.Nullable;
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
    @Pattern(regexp = "^[a-zA-Z0-9 ]*[a-zA-Z][a-zA-Z0-9 ]*$", message = "Product name must contain alphabetic characters and optionally numbers")
    private String name;

    // Description should not be empty
    @NotBlank(message = "Description cannot be empty")
    private String description;

    // Price should be a positive double
    @Positive(message = "Price must be positive")
    private double price;
    @Positive(message = "Quantity must be positive")
    private int quantity;  // Default value set to 1, validation ensures it's 1 or more
    // Image should be a string but there's no need for specific validation unless you require something specific like format checking
    @Nullable
    private String image;
    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
