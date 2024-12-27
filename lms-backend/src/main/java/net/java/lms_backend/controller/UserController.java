package net.java.lms_backend.controller;

import net.java.lms_backend.Service.UserService;
import net.java.lms_backend.dto.UpdateUser;
import net.java.lms_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/view/{id}")
    public ResponseEntity<String> view(@PathVariable long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().toString());
        }
        return ResponseEntity.notFound().build();
    }

    // Update user details
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody UpdateUser updatedUser) {
       return userService.updateUser(id,updatedUser);
    }
}
