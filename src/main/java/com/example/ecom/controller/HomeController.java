package com.example.ecom.controller;

import com.example.ecom.model.Category;
import com.example.ecom.model.Product;
import com.example.ecom.service.CategoryService;
import com.example.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping()
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String home(Model m) {
        // Load featured products (bán chạy) - lấy 4 sản phẩm đầu tiên
        List<Product> featuredProducts = productService.getAllActiveProductPagination(0, 4, "").getContent();
        m.addAttribute("featuredProducts", featuredProducts);

        // Load featured categories - lấy 3 danh mục đầu tiên
        List<Category> allCategories = categoryService.getAllCategory();
        List<Category> featuredCategories = allCategories.stream().limit(3).toList();
        m.addAttribute("featuredCategories", featuredCategories);

        return "home";
    }
}
