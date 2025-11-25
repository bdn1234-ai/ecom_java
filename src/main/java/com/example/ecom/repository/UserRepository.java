package com.example.ecom.repository;

import java.util.List;
import java.util.Optional;

import com.example.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRole(String role);


    Boolean existsByUsername(String email);
}
