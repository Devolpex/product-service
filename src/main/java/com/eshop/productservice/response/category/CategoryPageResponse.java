package com.eshop.productservice.response.category;

import java.util.ArrayList;
import java.util.List;

import com.eshop.productservice.request.category.CategoryPageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageResponse {
    private List<CategoryPageRequest> category = new ArrayList<>();
    private int currentPage;
    private int totalPages;
}
