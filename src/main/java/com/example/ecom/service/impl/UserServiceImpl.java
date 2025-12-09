package com.example.ecom.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecom.model.User;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.service.UserService;
import com.example.ecom.utils.AppConstant;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setRole("ROLE_USER");
        user.setIsEnable(true);
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Boolean updateAccountStatus(Integer id, Boolean status) {
        Optional<User> findByuser = userRepository.findById(id);

        if (findByuser.isPresent()) {
            User user = findByuser.get();
            user.setIsEnable(status);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void increaseFailedAttempt(User user) {
        int attempt = user.getFailedAttempt() + 1;
        user.setFailedAttempt(attempt);
        userRepository.save(user);
    }

    @Override
    public void userAccountLock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
    }



    @Override
    public boolean unlockAccountTimeExpired(User user) {
        long lockTime = user.getLockTime().getTime();
		long unLockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;

		long currentTime = System.currentTimeMillis();

		if (unLockTime < currentTime) {
			user.setAccountNonLocked(true);
			user.setFailedAttempt(0);
			user.setLockTime(null);
			userRepository.save(user);
			return true;
		}

		return false;
    }

    @Override
    public void resetAttempt(int userId) {
        
    }

    @Override
    public void updateUserResetToken(String email, String resetToken) {
        User findByEmail = userRepository.findByEmail(email);
		findByEmail.setResetToken(resetToken);
		userRepository.save(findByEmail);
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.findByResetToken(token);
    }

    @Override
    public User updateUser(User user) {
       return userRepository.save(user);
    }
    @Override
    public User updateUserProfile(User user, MultipartFile img) {
        try {
            if (img != null && !img.isEmpty()) {
                String imageName = img.getOriginalFilename();
                user.setProfileImage(imageName);
                
                // Save the image file
                String uploadDir = "uploads/img/user_profile_img/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                
                Path path = Paths.get(uploadDir + imageName);
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            
            User updateUser = userRepository.save(user);
            return updateUser;
        } catch (IOException | RuntimeException e) {
            System.out.println("Error during profile update: " + e.getMessage());
            return null;
        }
    }

}
