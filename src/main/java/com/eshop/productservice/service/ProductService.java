package com.eshop.productservice.service;


import com.eshop.productservice.model.Product;
import com.eshop.productservice.repository.ProductRepository;
import com.eshop.productservice.request.product.ProductCreateRequest;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;



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

        return product;
    }

}
