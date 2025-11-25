package com.example.ecom.service;

import java.util.List;

import com.example.ecom.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    void createCategory(Category category, MultipartFile file);

    List<Category> getAllCategory();
    Boolean deleteCategory(int id);
    Page<Category> getAllCategorPagination(Integer pageNo, Integer pageSize);

}
