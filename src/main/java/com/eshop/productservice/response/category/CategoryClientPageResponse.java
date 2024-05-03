package com.eshop.productservice.response.category;

import java.util.ArrayList;
import java.util.List;

import com.eshop.productservice.request.category.CategoryClientPageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryClientPageResponse {
    private List<CategoryClientPageRequest> category = new ArrayList<>();
    private int currentPage;
    private int totalPages;
}
