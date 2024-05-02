package com.eshop.productservice.Response.product;

import com.eshop.productservice.dto.product.ProductDto;
import com.eshop.productservice.dto.product.ProductHomeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductHomeResponse {
    private List<ProductHomeDto> products = new ArrayList<>();
    private int currentPage;
    private int totalPages;
}
