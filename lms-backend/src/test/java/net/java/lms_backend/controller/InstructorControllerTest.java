package net.java.lms_backend.controller;

import net.java.lms_backend.Service.SubmissionService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InstructorControllerTest {

    @Mock
    private SubmissionService submissionService;

    @Mock
    private SubmissionMapper submissionMapper;

    private InstructorController instructorController;
    private SubmissionDTO submissionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instructorController = new InstructorController(submissionService);

        submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void createSubmission() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "test content".getBytes()
        );

        when(submissionService.createSubmission(1L, 1L, file)).thenReturn(submissionDTO);

        ResponseEntity<SubmissionDTO> response = instructorController.createSubmission(1L, 1L, file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(submissionDTO, response.getBody());
        verify(submissionService).createSubmission(1L, 1L, file);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getSubmissionsByAssignmentId() {
        List<SubmissionDTO> submissions = Arrays.asList(submissionDTO);
        when(submissionService.getSubmissionsByAssignmentId(1L)).thenReturn(submissions);

        ResponseEntity<List<SubmissionDTO>> response = instructorController.getSubmissionsByAssignmentId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(submissions, response.getBody());
        verify(submissionService).getSubmissionsByAssignmentId(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void updateSubmissionGrade() {
        SubmissionDTO.SubmissionGradeAndFeedbackDTO gradeDTO = new SubmissionDTO.SubmissionGradeAndFeedbackDTO();
        gradeDTO.setGrade(85.0);
        gradeDTO.setFeedback("Good work");

        when(submissionService.patchSubmissionGradeAndFeedback(1L, 85.0, "Good work"))
                .thenReturn(submissionDTO);

        SubmissionDTO response = instructorController.updateSubmissionGrade(1L, gradeDTO);

        assertEquals(submissionDTO, response);
        verify(submissionService).patchSubmissionGradeAndFeedback(1L, 85.0, "Good work");
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getAssignmentPerformance() {
        when(submissionService.getAverageGradeByAssignmentId(1L)).thenReturn(85.0);
        when(submissionService.getNonGradedSubmissionsByAssignmentId(1L)).thenReturn(2.0);

        ResponseEntity<Map<String, Object>> response = instructorController.getAssignmentPerformance(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(85.0, response.getBody().get("average grades"));
        assertEquals(2.0, response.getBody().get("non graded submissions"));
        verify(submissionService).getAverageGradeByAssignmentId(1L);
        verify(submissionService).getNonGradedSubmissionsByAssignmentId(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getSubmissionsByCourseAndStudent() {
        List<SubmissionDTO> submissions = Arrays.asList(submissionDTO);
        when(submissionService.getSubmissionsByStudentIdAndCourseId(1L, 1L)).thenReturn(submissions);

        ResponseEntity<List<SubmissionDTO>> response = instructorController.getSubmissionsByCourseAndStudent(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(submissions, response.getBody());
        verify(submissionService).getSubmissionsByStudentIdAndCourseId(1L, 1L);
    }
}