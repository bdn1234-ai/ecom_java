package com.example.ecom.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecom.model.Category;

public interface CategoryService {

	public Category saveCategory(Category category);

	public Boolean existCategory(String name);

	public List<Category> getAllCategory();
	
	public Category getCategoryById(int id);

	public void createCategory(Category category, MultipartFile file);

	public Boolean deleteCategory(int id);

	public List<Category> getAllActiveCategory();

	public Page<Category> getAllCategoryPagination(Integer pageNo, Integer pageSize);
}
