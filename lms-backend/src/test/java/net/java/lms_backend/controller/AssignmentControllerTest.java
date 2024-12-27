package net.java.lms_backend.controller;

import net.java.lms_backend.Service.AssignmentService;
import net.java.lms_backend.dto.AssignmentDTO;
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

class AssignmentControllerTest {

    @Mock
    private AssignmentService assignmentService;
    private AssignmentController assignmentController;
    private AssignmentDTO assignmentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assignmentController = new AssignmentController(assignmentService);

        assignmentDTO = new AssignmentDTO();
        assignmentDTO.setId(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void createAssignment() {
        when(assignmentService.createAssignment(assignmentDTO)).thenReturn(assignmentDTO);

        ResponseEntity<AssignmentDTO> response = assignmentController.createAssignment(assignmentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assignmentDTO, response.getBody());
        verify(assignmentService).createAssignment(assignmentDTO);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getAssignmentById() {
        when(assignmentService.getAssignmentById(1L)).thenReturn(assignmentDTO);

        ResponseEntity<AssignmentDTO> response = assignmentController.getAssignmentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assignmentDTO, response.getBody());
        verify(assignmentService).getAssignmentById(1L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getAllAssignments() {
        List<AssignmentDTO> assignments = Arrays.asList(assignmentDTO);
        when(assignmentService.getAllAssignments()).thenReturn(assignments);

        ResponseEntity<List<AssignmentDTO>> response = assignmentController.getAllAssignments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assignments, response.getBody());
        verify(assignmentService).getAllAssignments();
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void updateAssignment() {
        when(assignmentService.updateAssignment(1L, assignmentDTO)).thenReturn(assignmentDTO);

        ResponseEntity<AssignmentDTO> response = assignmentController.updateAssignment(1L, assignmentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(assignmentDTO, response.getBody());
        verify(assignmentService).updateAssignment(1L, assignmentDTO);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void deleteAssignment() {
        ResponseEntity<Void> response = assignmentController.deleteAssignment(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(assignmentService).deleteAssignment(1L);
    }
}