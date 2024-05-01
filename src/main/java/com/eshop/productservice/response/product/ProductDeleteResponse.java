package com.eshop.productservice.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDeleteResponse {
    private String success;
    private List<String> errors;
    private String redirectTo;
}
