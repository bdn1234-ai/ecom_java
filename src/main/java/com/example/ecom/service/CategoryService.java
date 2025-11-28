package com.example.ecom.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecom.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
@Service
public interface CategoryService {

	public Category saveCategory(Category category);

	public Boolean existCategory(String name);

	List<Category> getAllCategory();

    void createCategory(Category category, MultipartFile file);

    Boolean deleteCategory(int id);
    Page<Category> getAllCategorPagination(Integer pageNo, Integer pageSize);

	public List<Category> getAllActiveCategory();

    public Category getCategoryById(int id);
}