package com.eshop.productservice.service;

import com.eshop.productservice.dto.product.ProductDto;
import com.eshop.productservice.dto.product.ProductHomeDto;
import com.eshop.productservice.model.Category;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.repository.CategoryRepository;
import com.eshop.productservice.repository.ProductRepository;
import com.eshop.productservice.request.product.ProductCreateRequest;

import com.eshop.productservice.request.product.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void saveProduct(ProductCreateRequest productRequest) {
        Product product = convertRequestToEntity(productRequest);
        productRepository.save(product);
    }

    public boolean productExist(String name) {
        return productRepository.existsProductByName(name);
    }

    public Product findProductByName(String name) {
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
        product.setQuantity(request.getQuantity());
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
        dto.setQuantity(product.getQuantity());
        dto.setImage(product.getImage());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
    private ProductHomeDto convertToDtoHome(Product product) {
        ProductHomeDto dto = new ProductHomeDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getShortDescription());
        dto.setPrice(product.getPrice());

        dto.setImage(product.getImage());
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
            dto.setCategoryId(product.getCategory().getId());
        }
        return dto;
    }
    // search product by name

    public Page<ProductDto> searchProducts(String search, Pageable pageable) {
        Page<Product> products = productRepository.findByProductNameOrCategoryName(search, pageable);
        return products.map(this::convertToDto);
    }

    public Page<ProductDto> getProductByPagination(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::convertToDto);
    }
    public Page<ProductHomeDto> getProductsByCategory(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryNameOrderByCreatedAtDesc(categoryId, pageable);
        return products.map(this::convertToDtoHome);
    }

    public Page<ProductHomeDto> getLastAllProduct(Pageable pageable) {
        Page<Product> products = productRepository.findLastAllProducts(pageable);
        return products.map(this::convertToDtoHome);
    }
    public List<ProductHomeDto> getLast6Product() {
        List<Product> products = productRepository.findLast6Products();
        return products.stream()
                .map(this::convertToDtoHome)
                .collect(Collectors.toList());

    }

    public Optional<Product> updateProduct(Long id, ProductUpdateRequest request) {
        return productRepository.findById(id).map(product -> {
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            if (request.getImage() != null) {
                product.setImage(request.getImage()); 
            }

            product.setQuantity(request.getQuantity());
            product.setUpdate_at(new Date());

            categoryRepository.findById(request.getCategoryId())
                    .ifPresent(product::setCategory);

            return productRepository.save(product);
        });
    }

    // Marouane Dbibih Service Functions

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findProductById(id);
        if (product != null) {
            return buildToDTO(product);
        }
        return null;
    }

    public ProductDto buildToDTO(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .quantity(product.getQuantity())
                .categoryName(product.getCategory().getName())
                .categoryId(product.getCategory().getId())
                .build();

    }
}
