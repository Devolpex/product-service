package com.eshop.productservice.dto.product;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private double price;
    private String image;
    private Date createdAt;
    private Date updatedAt;
    private int quantity;
    private long categoryId;
    private String categoryName;
}
