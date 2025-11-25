package com.example.ecom.service.auth.register.impl;

import com.example.ecom.model.User;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.service.auth.register.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(User user, MultipartFile file) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        user.setRole("ROLE_USER");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);


        User savUser = userRepository.save(user);
        if (ObjectUtils.isEmpty(savUser)) {
            throw new RuntimeException("Not saved ! internal server error");
        }
        if (file != null && !file.isEmpty()) {
            try {

                String uploadDir = "uploads/img/user_profile_img/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();

                }
                String fileName = savUser.getId() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                savUser.setProfileImage(fileName);
                userRepository.save(savUser);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving file: " + e.getMessage());
            }


        }
    }
}

