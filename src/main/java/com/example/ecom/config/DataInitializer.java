package com.example.ecom.config;

import com.example.ecom.model.User;
import com.example.ecom.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Kiểm tra nếu chưa có user admin
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("123456")) // mã hóa mật khẩu
                        .role("ADMIN")
                        .name("Bảo đẹp zai")
                        .build();
                userRepository.save(admin);
                System.out.println("✅ Admin account created: admin / 123456");
            } else {
                System.out.println("ℹ️ Admin account already exists");
            }
        };
    }
}
