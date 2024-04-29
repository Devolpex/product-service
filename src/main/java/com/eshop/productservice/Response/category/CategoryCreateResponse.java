package com.eshop.productservice.Response.category;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateResponse {
    private String success;
    private List<String> errors = new ArrayList<>();
    private String redirectTo;
}
