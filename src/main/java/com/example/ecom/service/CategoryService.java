package com.example.ecom.service;

import java.util.List;

import com.example.ecom.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
public interface CategoryService {

    void createCategory(Category category, MultipartFile file);

    List<Category> getAllCategory();
    Boolean deleteCategory(int id);

	// public Category getCategoryById(int id);

	public List<Category> getAllActiveCategory();

	Page<Category> getAllCategoryPagination(Integer pageNo,Integer pageSize);
}
