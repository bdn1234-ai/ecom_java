package com.example.ecom.controller;


import com.example.ecom.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping()
public class RegisterController {


    @GetMapping("/register")
    public String register() {
        return "register"; // Trả về tên của view đăng ký
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, @RequestParam("img") MultipartFile file, HttpSession session){
        return "redirect:/register";
    }
}
