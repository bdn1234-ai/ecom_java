package com.example.ecom.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class RegisterController {


    @GetMapping("/register")
    public String register() {
        return "register"; // Trả về tên của view đăng ký
    }
}
