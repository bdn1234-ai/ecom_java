package com.example.ecom.service.impl;

import java.util.List;
import org.springframework.data.domain.Page;
import com.example.ecom.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.ecom.repository.CategoryRepository;
import com.example.ecom.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
    @Override
    public Boolean deleteCategory(int id) {
        return null;
    }
    @Override
    public Category getCategoryById(int id) {
        return null;
    }
    @Override
    public List<Category> getAllActiveCategory() {
        return null;
    }
    @Override
    public Page<Category> getAllCategoryPagination(Integer pageNo, Integer pageSize) {
        return null;
    }
}
