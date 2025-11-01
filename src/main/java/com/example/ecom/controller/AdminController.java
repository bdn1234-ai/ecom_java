package com.example.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import com.example.ecom.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecom.service.CategoryService;
import com.example.ecom.service.ProductService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
	private ProductService productService;
    
    @GetMapping()
    public String index() {
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct() {
        return "admin/loadAddProduct";
    }

    @GetMapping("/category")
    public String category() {
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                               HttpSession session) {

        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";

        category.setImageName(imageName);
        
        Boolean existCategory = categoryService.existCategory(category.getName());

        if (existCategory) {
            session.setAttribute("errMsg", "Category Name already exists");
        } else {
            Category savCategory = categoryService.saveCategory(category);

            if (ObjectUtils.isEmpty(savCategory)) {
                session.setAttribute("errorMsg", "Not saved ! internal servel error");

            } else {

                try {
                    File saveFile = new ClassPathResource("static/img").getFile();

                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" +
                            File.separator + file.getOriginalFilename());

                    System.out.println("Saving file to: " + path);

                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                    session.setAttribute("successMsg", "Save successfully");

                } catch (IOException e) {
                    e.printStackTrace();
                    session.setAttribute("errorMsg", "Error saving file: " + e.getMessage());
                }

            }
        }
       
        return "redirect:/admin/category";
    }

    @GetMapping("/products")
    public String loadViewProduct(Model m) {
        return "admin/products";
    }
    
}