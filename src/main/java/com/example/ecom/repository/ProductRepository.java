package com.example.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecom.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

    public Boolean existsByTitle(String title);
}
