package com.example.junitandmocktioexample2.service;

import com.example.junitandmocktioexample2.entity.Product;
import com.example.junitandmocktioexample2.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product){
        
        log.info("Saving the product");
        boolean validation = validateProductName(product.getName());
        if(validation){
            return productRepository.save(product);
        }
        else{
            throw new RuntimeException("Product name is invalid");
        }

    }

    public void deleteProductById(Integer id){
        productRepository.deleteById(id);
    }

    private boolean validateProductName(String name){
        return name != null && !name.isEmpty();
    }
}
