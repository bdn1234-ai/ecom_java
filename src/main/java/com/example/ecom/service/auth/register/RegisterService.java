package com.example.ecom.service.auth.register;

import com.example.ecom.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {

    public void register(User user, MultipartFile file);
}
