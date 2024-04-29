package com.eshop.product.service;

import com.eshop.product.model.Product;
import com.eshop.product.repository.ProductRepository;
import com.eshop.product.request.product.ProductCreateRequest;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

//    @Autowired
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

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

    private Product convertRequestToEntity(ProductCreateRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImage(request.getImage());
        product.setCreated_at(new Date());
        return product;
    }
}
