package com.example.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecom.model.Category;
import com.example.ecom.model.Product;
import com.example.ecom.service.CategoryService;
import com.example.ecom.service.ProductService;


@Controller
@RequestMapping()
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam String ch, Model m) {
        List<Product> searchProducts = productService.searchProduct(ch);
        m.addAttribute("products", searchProducts);
        List<Category> categories = categoryService.getAllActiveCategory();
        m.addAttribute("categories", categories);
        return "product";
    }
    
}
