package com.eshop.productservice.Response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateResponse {
    private String success;
    private List<String> errors = new ArrayList<>();
    private String redirectTo;
}
