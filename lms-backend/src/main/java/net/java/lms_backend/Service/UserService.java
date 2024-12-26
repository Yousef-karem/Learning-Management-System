package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.UserRepository;
import net.java.lms_backend.dto.UpdateUser;
import net.java.lms_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;



    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
    }

    public Optional<User> getUser(long id)
    {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // First, try finding the user by username
        Optional<User> user = userRepository.findByUsername(identifier);

        // If the user is not found by username, try finding by email
        if (user.isEmpty()) {
            user = userRepository.findByEmail(identifier);
        }

        // If the user is still not found, throw UsernameNotFoundException
        User foundUser = user.orElseThrow(() ->
                new UsernameNotFoundException("User Not Found"));

        // Map the found User to a Spring Security UserDetails object
        return foundUser;
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
    public ResponseEntity<String> updateUser(Long id, UpdateUser updateUser) {
        // Get the current logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();  // Assuming the username is used as a unique identifier

        // Retrieve the user from the repository
        Optional<User> existingUser = userRepository.findById(id);

        if(existingUser.isPresent()) {
            User user = existingUser.get();

            // Check if the logged-in user is the one trying to update their own details
            if (!user.getUsername().equals(currentUsername)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not allowed to update another user's details.");
            }

            // Proceed with the update
            if(updateUser.getFirstName() != null) {
                user.setFirstName(updateUser.getFirstName());
            }
            if(updateUser.getLastName() != null) {
                user.setLastName(updateUser.getLastName());
            }
            if(updateUser.getPassword() != null) {
                user.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
            }

            String email = "";
            if(updateUser.getEmail() != null) {
                user.setEnabled(false);  // Assuming email update requires reactivation or confirmation
                user.setEmail(updateUser.getEmail());
                email = emailSender.sendEmail(user);
            }

            // Save the updated user back to the repository (make sure to call save)
            userRepository.save(user);

            return ResponseEntity.ok("User updated successfully\n" + email);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

}
