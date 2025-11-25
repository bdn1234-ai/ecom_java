package com.example.ecom.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {

    public void removeMessageFromSession() {
        try {
            System.out.println("Removing message from session");
            // Lấy ra Session hiện tại
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();

            // Xóa các thông báo đi để khi F5 không bị hiện lại
            session.removeAttribute("succMsg");
            session.removeAttribute("errorMsg");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
