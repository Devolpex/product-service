package com.eshop.productservice.response.product;

import java.util.ArrayList;
import java.util.List;

import com.eshop.productservice.dto.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageResponse {
    private List<ProductDto> products = new ArrayList<>();
    private int currentPage;
    private int totalPages;
}
