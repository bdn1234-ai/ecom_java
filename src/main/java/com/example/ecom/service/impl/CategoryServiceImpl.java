package com.example.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import com.example.ecom.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.example.ecom.repository.CategoryRepository;
import com.example.ecom.service.CategoryService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void createCategory(Category category, MultipartFile file) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category Name already exists");

        }
        Category savCategory = categoryRepository.save(category);

        if (ObjectUtils.isEmpty(savCategory)) {
            throw new RuntimeException("Not saved ! internal servel error");

        }
        if (file != null && !file.isEmpty())
            try {

                String uploadDir = "uploads/img/category_img/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                Path path = Paths.get(uploadDir + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                savCategory.setImageName(file.getOriginalFilename());
                categoryRepository.save(savCategory);


            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving file: " + e.getMessage());
            }
    }



    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
    @Override
    public Boolean deleteCategory(int id) {
        if (!categoryRepository.existsById(id)) {
            return false;
        }
        categoryRepository.deleteById(id);
        return true;
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAllActiveCategory() {
        return categoryRepository.findAll().stream()
                .filter(c -> Boolean.TRUE.equals(c.getIsActive()))
                .collect(Collectors.toList());
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }
    @Override
    public Page<Category> getAllCategoryPagination(Integer pageNo, Integer pageSize) {
        return categoryRepository.findAll(PageRequest.of(pageNo, pageSize));
    }


}
