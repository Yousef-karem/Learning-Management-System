package net.java.lms_backend.Security;

import net.java.lms_backend.Repositrory.UserRepository;
import net.java.lms_backend.entity.Role;
import net.java.lms_backend.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationInitializer {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationInitializer(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            // Check if the admin already exists
            if (userRepository.count() == 0) {
                // Create the admin user
                User admin = new User();
                admin.setUsername("FcaiAdmin");  // Set the admin username
                admin.setPassword( bCryptPasswordEncoder.encode("1234"));  // Set the password and encode it
                admin.setEnabled(true);
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                User student = new User();
                student.setUsername("Evra");
                student.setPassword( bCryptPasswordEncoder.encode("1234"));
                student.setEnabled(true);
                student.setRole(Role.STUDENT);
                userRepository.save(student);
                User instructor = new User();
                instructor.setUsername("joo91");
                instructor.setPassword( bCryptPasswordEncoder.encode("1234"));
                instructor.setEnabled(true);
                instructor.setRole(Role.INSTRUCTOR);
                userRepository.save(instructor);
            }
        };
    }
}
