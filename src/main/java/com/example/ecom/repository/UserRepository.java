package com.example.ecom.repository;

import java.util.List;
import java.util.Optional;

import com.example.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public List<User> findByRole(String role);

	public User findByResetToken(String token);

	public Boolean existsByEmail(String email);

	// Added to support username-based lookup used in authentication
	public Optional<User> findByUsername(String username);

	public Boolean existsByUsername(String username);
}
