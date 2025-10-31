package com.example.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


import com.example.ecom.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecom.service.CategoryService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String index() {
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct() {
        return "admin/loadAddProduct";
    }

    @GetMapping("/category")
    public String category(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        // m.addAttribute("categorys", categoryService.getAllCategory());
        Page<Category> page = categoryService.getAllCategorPagination(pageNo, pageSize);
        List<Category> categorys = page.getContent();
        m.addAttribute("categorys", categorys);

        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());

        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                               HttpSession session) {

        Boolean existCategory = categoryService.existCategory(category.getName());

        if (existCategory) {
            session.setAttribute("errMsg", "Category Name already exists");
        } else {
            Category savCategory = categoryService.saveCategory(category);

            if (ObjectUtils.isEmpty(savCategory)) {
                session.setAttribute("errorMsg", "Not saved ! internal servel error");

            } else {

                    try {
                        // ✅ Đường dẫn thực tế tới static/img/category_img
                        String uploadDir = "uploads/img/category_img/";
                        File directory = new File(uploadDir);
                        if (!directory.exists()) {
                            directory.mkdirs(); // Tạo nếu chưa có
                        }

                        Path path = Paths.get(uploadDir + file.getOriginalFilename());
                        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                        category.setImageName(file.getOriginalFilename());
                        categoryService.saveCategory(category);

                        session.setAttribute("successMsg", "Save successfully");

                    } catch (IOException e) {
                        e.printStackTrace();
                        session.setAttribute("errorMsg", "Error saving file: " + e.getMessage());
                    }

                }
        }
       
        return "redirect:/admin/category";
    }

}