package net.java.lms_backend.controller;

import net.java.lms_backend.Service.StudentService;
import net.java.lms_backend.Service.SubmissionService;
import net.java.lms_backend.dto.StudentDTO;
import net.java.lms_backend.dto.SubmissionDTO;
import net.java.lms_backend.mapper.SubmissionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private SubmissionService submissionService;

    @Mock
    private SubmissionMapper submissionMapper;

    private StudentController studentController;

    private StudentDTO studentDTO;
    private SubmissionDTO submissionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentController = new StudentController(studentService, submissionService, submissionMapper);

        studentDTO = new StudentDTO();
        studentDTO.setId(1L);
        studentDTO.setUsername("Test Student");

        submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);
        submissionDTO.setStudentId(1L);
        submissionDTO.setAssignmentId(1L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void createSubmission() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "test content".getBytes()
        );

        when(submissionService.createSubmission(1L, 1L, file)).thenReturn(submissionDTO);

        ResponseEntity<SubmissionDTO> response = studentController.createSubmission(1L, 1L, file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(submissionDTO, response.getBody());
        verify(submissionService).createSubmission(1L, 1L, file);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getAllSubmissions() {
        List<SubmissionDTO> submissions = Arrays.asList(submissionDTO);
        when(submissionService.getAllSubmissions()).thenReturn(submissions);

        ResponseEntity<List<SubmissionDTO>> response = studentController.getAllSubmissions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(submissions, response.getBody());
        verify(submissionService).getAllSubmissions();
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getSubmissionById() {
        when(submissionService.getSubmissionById(1L)).thenReturn(submissionDTO);

        ResponseEntity<SubmissionDTO> response = studentController.getSubmissionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(submissionDTO, response.getBody());
        verify(submissionService).getSubmissionById(1L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void deleteSubmission() {
        ResponseEntity<Void> response = studentController.deleteSubmission(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(submissionService).deleteSubmission(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllStudents() {
        List<StudentDTO> students = Arrays.asList(studentDTO);
        when(studentService.getAllStudents()).thenReturn(students);

        ResponseEntity<List<StudentDTO>> response = studentController.getAllStudents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
        verify(studentService).getAllStudents();
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getStudentById() {
        when(studentService.getStudentById(1L)).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.getStudentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDTO, response.getBody());
        verify(studentService).getStudentById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getStudentByIdNotFound() {
        when(studentService.getStudentById(1L)).thenReturn(null);

        ResponseEntity<StudentDTO> response = studentController.getStudentById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(studentService).getStudentById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createStudent() {
        when(studentService.createStudent(any(StudentDTO.class))).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.createStudent(studentDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(studentDTO, response.getBody());
        verify(studentService).createStudent(any(StudentDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateStudent() {
        when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenReturn(studentDTO);

        ResponseEntity<StudentDTO> response = studentController.updateStudent(1L, studentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDTO, response.getBody());
        verify(studentService).updateStudent(eq(1L), any(StudentDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateStudentNotFound() {
        when(studentService.updateStudent(eq(1L), any(StudentDTO.class))).thenReturn(null);

        ResponseEntity<StudentDTO> response = studentController.updateStudent(1L, studentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(studentService).updateStudent(eq(1L), any(StudentDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteStudent() {
        when(studentService.deleteStudent(1L)).thenReturn(true);

        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService).deleteStudent(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteStudentNotFound() {
        when(studentService.deleteStudent(1L)).thenReturn(false);

        ResponseEntity<Void> response = studentController.deleteStudent(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(studentService).deleteStudent(1L);
    }
}