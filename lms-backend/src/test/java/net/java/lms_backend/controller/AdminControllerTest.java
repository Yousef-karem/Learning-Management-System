package net.java.lms_backend.controller;

import net.java.lms_backend.Service.AdminService;
import net.java.lms_backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private AdminService adminService;
    private AdminController adminController;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminController = new AdminController(adminService);

        testUser = new User();
        testUser.setId(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers() {
        List<User> users = Arrays.asList(testUser);
        when(adminService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = adminController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(adminService).getAllUsers();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void assignRole_Success() {
        when(adminService.assignRole(1L, "INSTRUCTOR")).thenReturn(true);

        ResponseEntity<String> response = adminController.assignRole(1L, "INSTRUCTOR");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Role assigned successfully", response.getBody());
        verify(adminService).assignRole(1L, "INSTRUCTOR");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void assignRole_Failure() {
        when(adminService.assignRole(1L, "INVALID_ROLE")).thenReturn(false);

        ResponseEntity<String> response = adminController.assignRole(1L, "INVALID_ROLE");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to assign role. Invalid role or user.", response.getBody());
        verify(adminService).assignRole(1L, "INVALID_ROLE");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deactivateUser_Success() {
        when(adminService.deactivateUser(1L)).thenReturn(true);

        ResponseEntity<String> response = adminController.deactivateUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deactivated successfully", response.getBody());
        verify(adminService).deactivateUser(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deactivateUser_Failure() {
        when(adminService.deactivateUser(1L)).thenReturn(false);

        ResponseEntity<String> response = adminController.deactivateUser(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to deactivate user. User not found.", response.getBody());
        verify(adminService).deactivateUser(1L);
    }
}