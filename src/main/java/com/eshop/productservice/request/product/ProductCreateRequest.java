package com.eshop.productservice.request.product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "Product Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*[a-zA-Z][a-zA-Z0-9 ]*$", message = "Product name must contain alphabetic characters and optionally numbers")
    private String name;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Positive(message = "Price must be positive")
    private double price;

    @Positive(message = "Quantity must be positive")
    private int quantity;

    @NotBlank(message = "Image cannot be empty")
    private String image;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be positive")
    private Long categoryId;

}
