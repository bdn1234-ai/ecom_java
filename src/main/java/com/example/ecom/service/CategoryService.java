package com.example.ecom.service;

import java.util.List;

import com.example.ecom.model.Category;


public interface CategoryService {

    public Category saveCategory(Category category);

    public Boolean existCategory(String name);

    public List<Category> getAllCategory();


}
