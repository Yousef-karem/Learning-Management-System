package net.java.lms_backend.controller;

import net.java.lms_backend.Service.AuthService;
import net.java.lms_backend.dto.LoginRequestDTO;
import net.java.lms_backend.dto.RegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static net.java.lms_backend.mapper.UserMapper.ToUserLogin;

@RestController
@RequestMapping(path = "api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<String> Login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.Login( ToUserLogin(loginRequest));
    }

    @PostMapping("register")
    public ResponseEntity<String> Register(@RequestBody RegisterDTO user) {
        return authService.register(user);
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return authService.confirmToken(token);
    }
}
