package com.example.junitandmocktioexample2.controller;
import com.example.junitandmocktioexample2.entity.Product;
import com.example.junitandmocktioexample2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/addproduct")
    public Product addProduct(Product product){
        return productService.addProduct(product);
    }

    @DeleteMapping("/deleteProductById")
    public void deleteProductById(Integer id){
        productService.deleteProductById(id);
    }
}
