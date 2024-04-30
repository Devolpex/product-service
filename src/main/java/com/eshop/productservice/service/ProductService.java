package com.eshop.productservice.service;


import com.eshop.productservice.dto.product.ProductDto;
import com.eshop.productservice.model.Category;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.repository.CategoryRepository;
import com.eshop.productservice.repository.ProductRepository;
import com.eshop.productservice.request.product.ProductCreateRequest;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;




import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;



    public void saveProduct(ProductCreateRequest productRequest) {
        Product product = convertRequestToEntity(productRequest);
        productRepository.save(product);
    }
    public boolean productExist(String name){
        return productRepository.existsProductByName(name);
    }
    public Product findProductByName(String name){
        Product product = productRepository.findProductByName(name);
        return product;
    }
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Product convertRequestToEntity(ProductCreateRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImage(request.getImage());
        product.setCreated_at(new Date());
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        category.ifPresent(product::setCategory);
        return product;
    }
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
    //search product by name

    public Page<ProductDto> searchProducts(String search, Pageable pageable) {
        Page<Product> products = productRepository.findByProductNameOrCategoryName(search, pageable);
        return products.map(this::convertToDto);
    }


    public Page<ProductDto> getProductByPagination(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::convertToDto);
    }
}
