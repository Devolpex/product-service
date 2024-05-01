package com.eshop.productservice.response.product;

import java.util.ArrayList;
import java.util.List;

import com.eshop.productservice.dto.product.ProductDto;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.response.settings.Notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private ProductDto product;
    private List<ProductDto> listProducts = new ArrayList<>();
    private Notification notification;

}
