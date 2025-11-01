package com.example.ecom.service;

import java.util.List;

import com.example.ecom.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
@Service
public interface CategoryService {

    public Category saveCategory(Category category);

    public Boolean existCategory(String name);

    public List<Category> getAllCategory();

    public Boolean deleteCategory(int id);

	public Category getCategoryById(int id);

	public List<Category> getAllActiveCategory();

	public Page<Category> getAllCategoryPagination(Integer pageNo,Integer pageSize);
}
