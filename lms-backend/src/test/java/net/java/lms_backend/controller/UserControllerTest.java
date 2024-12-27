package net.java.lms_backend.controller;

import net.java.lms_backend.Service.UserService;
import net.java.lms_backend.dto.UpdateUser;
import net.java.lms_backend.entity.User;
import net.java.lms_backend.Security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User testUser;
    private UpdateUser updateUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("Test User");
        testUser.setEmail("test@example.com");

        updateUser = new UpdateUser();
        updateUser.setFirstName("Updated User");
        updateUser.setEmail("updated@example.com");

        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void view_WhenUserExists_ReturnsOkResponse() {
        when(userService.getUser(1L)).thenReturn(Optional.of(testUser));
        ResponseEntity<String> response = userController.view(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains(testUser.toString()));
        verify(userService, times(1)).getUser(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void view_WhenUserDoesNotExist_ReturnsNotFoundResponse() {
        when(userService.getUser(999L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = userController.view(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).getUser(999L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WhenSuccessful_ReturnsSuccessResponse() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("User updated successfully");
        when(userService.updateUser(1L, updateUser)).thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.updateUser(1L, updateUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        verify(userService, times(1)).updateUser(1L, updateUser);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WhenUserNotFound_ReturnsNotFoundResponse() {
        ResponseEntity<String> expectedResponse = ResponseEntity.notFound().build();
        when(userService.updateUser(999L, updateUser)).thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.updateUser(999L, updateUser);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).updateUser(999L, updateUser);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_WhenBadRequest_ReturnsBadRequestResponse() {
        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Invalid input");
        when(userService.updateUser(1L, updateUser)).thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.updateUser(1L, updateUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
        verify(userService, times(1)).updateUser(1L, updateUser);
    }

    // Additional security-related tests
    @Test
    void testUnauthorizedAccess() {
        when(userService.getUser(1L)).thenReturn(Optional.of(testUser));
        ResponseEntity<String> response = userController.view(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testInsufficientPrivileges() {
        when(userService.getUser(1L)).thenReturn(Optional.of(testUser));
        ResponseEntity<String> response = userController.view(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}