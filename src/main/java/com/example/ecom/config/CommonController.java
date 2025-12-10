package com.example.ecom.config;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.ecom.model.User;
import com.example.ecom.service.CartService;
import com.example.ecom.service.UserService;

@ControllerAdvice
public class CommonController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            User user = userService.getUserByUsername(username);
            m.addAttribute("user", user);

            if (user != null) {
                Integer countCart = cartService.getCountCart(user.getId());
                m.addAttribute("countCart", countCart);
            }
        }
    }
}