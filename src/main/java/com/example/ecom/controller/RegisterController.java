package com.example.ecom.controller;


import com.example.ecom.model.User;
import com.example.ecom.service.auth.register.RegisterService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {
    private final RegisterService registerService;
    @GetMapping()
    public String register() {
        return "register";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute User user, @RequestParam("file") MultipartFile file, HttpSession session){
        try{
            registerService.register(user,file);
            session.setAttribute("successMsg","Register Successfully ! ");
        } catch (Exception e) {
            session.setAttribute("errorMsg","Error saving file: " + e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/login";
    }
}
