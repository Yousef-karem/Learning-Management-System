package net.java.lms_backend.controller;

import net.java.lms_backend.Service.QuizService;
import net.java.lms_backend.dto.QuizDTO;
import net.java.lms_backend.entity.Quiz;
import net.java.lms_backend.entity.QuizAttempt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuizControllerTest {

    @Mock
    private QuizService quizService;
    private QuizController quizController;
    private Quiz quiz;
    private QuizDTO quizDTO;
    private QuizAttempt quizAttempt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizController = new QuizController(quizService);

        quiz = new Quiz();
        quiz.setId(1L);

        quizDTO = new QuizDTO();
        quizDTO.setNumOfMCQ(5L);
        quizDTO.setNumOfTrueFalse(3L);
        quizDTO.setNumOfShortAnswer(2L);

        quizAttempt = new QuizAttempt();
        quizAttempt.setId(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void createQuiz() {
        when(quizService.createQuiz(1L, quizDTO)).thenReturn(quiz);

        ResponseEntity<Quiz> response = quizController.createQuiz(1L, quizDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quiz, response.getBody());
        verify(quizService).createQuiz(1L, quizDTO);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void createQuizWithDifferentQuestionCounts() {
        QuizDTO customQuizDTO = new QuizDTO();
        customQuizDTO.setNumOfMCQ(10L);
        customQuizDTO.setNumOfTrueFalse(5L);
        customQuizDTO.setNumOfShortAnswer(3L);

        Quiz customQuiz = new Quiz();
        customQuiz.setId(2L);

        when(quizService.createQuiz(1L, customQuizDTO)).thenReturn(customQuiz);

        ResponseEntity<Quiz> response = quizController.createQuiz(1L, customQuizDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customQuiz, response.getBody());
        verify(quizService).createQuiz(1L, customQuizDTO);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void updateQuizAttempt() {
        Map<Long, String> answers = new HashMap<>();
        answers.put(1L, "Answer 1");
        answers.put(2L, "true");
        answers.put(3L, "Short answer response");

        when(quizService.updateQuizAttempt(1L, answers)).thenReturn(80);

        ResponseEntity<Integer> response = quizController.updateQuizAttempt(1L, answers);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(80, response.getBody());
        verify(quizService).updateQuizAttempt(1L, answers);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getQuizAttempt() {
        when(quizService.getQuizAttempt(1L)).thenReturn(quizAttempt);

        ResponseEntity<QuizAttempt> response = quizController.getQuizAttempt(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(quizAttempt, response.getBody());
        verify(quizService).getQuizAttempt(1L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getQuizPerformance() {
        when(quizService.getAverageScoreByQuizId(1L)).thenReturn(85.0);

        ResponseEntity<Integer> response = quizController.getQuizPerformance(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(85, response.getBody());
        verify(quizService).getAverageScoreByQuizId(1L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getQuizAttemptsByStudent() {
        List<QuizAttempt> attempts = Arrays.asList(quizAttempt);
        when(quizService.getQuizAttemptsByStudent(1L, 1L)).thenReturn(attempts);

        List<QuizAttempt> response = quizController.getQuizAttemptsByStudent(1L, 1L);

        assertEquals(attempts, response);
        verify(quizService).getQuizAttemptsByStudent(1L, 1L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getAverageScoreByStudent() {
        when(quizService.getAverageScoreOfStudent(1L, 1L)).thenReturn(90.0);

        Double response = quizController.getAverageScoreByStudent(1L, 1L);

        assertEquals(90.0, response);
        verify(quizService).getAverageScoreOfStudent(1L, 1L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void getQuizAttemptWithZeroScore() {
        QuizAttempt emptyAttempt = new QuizAttempt();
        emptyAttempt.setId(2L);

        when(quizService.getQuizAttempt(2L)).thenReturn(emptyAttempt);

        ResponseEntity<QuizAttempt> response = quizController.getQuizAttempt(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyAttempt, response.getBody());
        verify(quizService).getQuizAttempt(2L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void updateQuizAttemptWithEmptyAnswers() {
        Map<Long, String> emptyAnswers = new HashMap<>();
        when(quizService.updateQuizAttempt(1L, emptyAnswers)).thenReturn(0);

        ResponseEntity<Integer> response = quizController.updateQuizAttempt(1L, emptyAnswers);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody());
        verify(quizService).updateQuizAttempt(1L, emptyAnswers);
    }
}