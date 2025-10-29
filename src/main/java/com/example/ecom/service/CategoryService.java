package com.example.ecom.service;

import java.util.List;

import com.example.ecom.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    public Category saveCategory(Category category);

    public Boolean existCategory(String name);

    public List<Category> getAllCategory();

    public Page<Category> getAllCategorPagination(Integer pageNo, Integer pageSize);

}
