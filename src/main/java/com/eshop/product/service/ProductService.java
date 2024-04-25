package com.eshop.product.service;

import com.eshop.product.model.Product;
import com.eshop.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
