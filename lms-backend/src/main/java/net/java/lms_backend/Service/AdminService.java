package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.ConfirmationTokenRepository;
import net.java.lms_backend.entity.User;
import net.java.lms_backend.entity.Role;
import net.java.lms_backend.Repositrory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    public AdminService(UserRepository userRepository, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    // Get all users in the system
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Assign a role to a user
    public boolean assignRole(Long userId, String role) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Role newRole = Role.valueOf(role.toUpperCase());

            // Check if the role is valid
            if (newRole == null) {
                return false; // Invalid role
            }
            if(user.isInitialAdmin()) {
                return false;
            }
            user.setRole(newRole);
            userRepository.save(user);
            return true;
        }
        return false; // User not found
    }


    // Deactivate a user account
    public boolean deactivateUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEnabled(false); // Disable the account
            userRepository.save(user);
            return true;
        }
        return false; // User not found
    }
}
