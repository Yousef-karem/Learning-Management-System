package net.java.lms_backend.controller;

import net.java.lms_backend.Service.QuestionService;
import net.java.lms_backend.dto.QuestionDTO;
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

class QuestionControllerTest {

    @Mock
    private QuestionService questionService;
    private QuestionController questionController;
    private QuestionDTO questionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionController = new QuestionController(questionService);

        questionDTO = new QuestionDTO();
        questionDTO.setId(1L);
        questionDTO.setContent("Test question");
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void createQuestion() {
        when(questionService.createQuestion(questionDTO)).thenReturn(questionDTO);

        ResponseEntity<QuestionDTO> response = questionController.createQuestion(questionDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questionDTO, response.getBody());
        verify(questionService).createQuestion(questionDTO);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getAllQuestions() {
        List<QuestionDTO> questions = Arrays.asList(questionDTO);
        when(questionService.getAllQuestions()).thenReturn(questions);

        ResponseEntity<List<QuestionDTO>> response = questionController.getAllQuestions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questions, response.getBody());
        verify(questionService).getAllQuestions();
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getQuestionById() {
        when(questionService.getQuestionById(1L)).thenReturn(questionDTO);

        ResponseEntity<QuestionDTO> response = questionController.getQuestionById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questionDTO, response.getBody());
        verify(questionService).getQuestionById(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void updateQuestion() {
        when(questionService.updateQuestion(1L, questionDTO)).thenReturn(questionDTO);

        ResponseEntity<QuestionDTO> response = questionController.updateQuestion(1L, questionDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questionDTO, response.getBody());
        verify(questionService).updateQuestion(1L, questionDTO);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void deleteQuestion() {
        ResponseEntity<Void> response = questionController.deleteQuestion(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(questionService).deleteQuestion(1L);
    }
}