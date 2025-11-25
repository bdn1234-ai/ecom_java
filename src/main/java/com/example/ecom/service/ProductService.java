package com.example.ecom.service;

import com.example.ecom.model.Product;

public interface ProductService {

    public Product saveProduct(Product product);

    public Boolean existProduct(String title);
}
