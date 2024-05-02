package com.eshop.productservice.response.product;

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
public class ProductCreateResponse {
    private String success;
    private List<String> errors = new ArrayList<>();
    private String redirectTo;
}
