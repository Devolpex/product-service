package com.eshop.productservice.request.category;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequest {
    @Pattern(regexp = "[a-zA-Z]+", message = "Category Name must contain only alphabetic characters")
    @NotBlank(message = "Category Name can not be empty")
    private String name;
    @NotBlank(message = "Description cannot be empty")
    private String description;
}
