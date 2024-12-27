package net.java.lms_backend.controller;

import net.java.lms_backend.Service.AuthService;
import net.java.lms_backend.dto.LoginRequestDTO;
import net.java.lms_backend.dto.RegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @Mock
    private AuthService authService;
    private AuthController authController;
    private LoginRequestDTO loginRequestDTO;
    private RegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authService);

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("test@test.com");
        loginRequestDTO.setPassword("password");

        registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@test.com");
        registerDTO.setPassword("password");
    }

    @Test
    void login_Success() {
        when(authService.Login(any())).thenReturn(ResponseEntity.ok("JWT Token"));

        ResponseEntity<String> response = authController.Login(loginRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("JWT Token", response.getBody());
        verify(authService).Login(any());
    }

    @Test
    void register_Success() {
        when(authService.register(registerDTO)).thenReturn(ResponseEntity.ok("Registration successful"));

        ResponseEntity<String> response = authController.Register(registerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registration successful", response.getBody());
        verify(authService).register(registerDTO);
    }

    @Test
    void confirmToken_Success() {
        String token = "valid-token";
        when(authService.confirmToken(token)).thenReturn(ResponseEntity.ok("Email confirmed"));

        ResponseEntity<String> response = authController.confirm(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email confirmed", response.getBody());
        verify(authService).confirmToken(token);
    }

    @Test
    void login_Failure() {
        when(authService.Login(any())).thenReturn(ResponseEntity.badRequest().body("Invalid credentials"));

        ResponseEntity<String> response = authController.Login(loginRequestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid credentials", response.getBody());
        verify(authService).Login(any());
    }

    @Test
    void register_Failure() {
        when(authService.register(registerDTO)).thenReturn(ResponseEntity.badRequest().body("Email already exists"));

        ResponseEntity<String> response = authController.Register(registerDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
        verify(authService).register(registerDTO);
    }

    @Test
    void confirmToken_Failure() {
        String token = "invalid-token";
        when(authService.confirmToken(token)).thenReturn(ResponseEntity.badRequest().body("Invalid token"));

        ResponseEntity<String> response = authController.confirm(token);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid token", response.getBody());
        verify(authService).confirmToken(token);
    }
}