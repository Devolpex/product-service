package com.eshop.product.request.product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "Product Name cannot be empty")
    @Pattern(regexp = "[a-zA-Z]+[0-9]*", message = "Product name must contain alphabetic characters and optionally numbers")
    private String name;

    // Description should not be empty
    @NotBlank(message = "Description cannot be empty")
    private String description;

    // Price should be a positive double
    @Positive(message = "Price must be positive")
    private double price;

    // Image should be a string but there's no need for specific validation unless you require something specific like format checking
    private String image;


}
