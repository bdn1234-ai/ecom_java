package com.example.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.ecom.model.ProductOrder;


public interface ProductOrderRepository extends JpaRepository<ProductOrder , Integer> {
    
    List<ProductOrder> findByUserId(Integer userId);
}
