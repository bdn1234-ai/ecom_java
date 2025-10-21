package com.example.ecom.service.auth;


import com.example.ecom.model.security.CustomUser;
import com.example.ecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUser(
                userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username))
        );
    }
}
