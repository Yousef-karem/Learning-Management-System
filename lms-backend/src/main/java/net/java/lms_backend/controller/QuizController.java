package net.java.lms_backend.controller;

import net.java.lms_backend.Service.QuizService;
import net.java.lms_backend.dto.QuizDTO;
import net.java.lms_backend.entity.Quiz;
import net.java.lms_backend.entity.QuizAttempt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create/{courseId}")
    public ResponseEntity<Quiz> createQuiz(@PathVariable Long courseId, @RequestBody QuizDTO quizDTO) {
        Quiz quiz = quizService.createQuiz(courseId, quizDTO);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/attempt/{quizId}/student/{studentId}")
    public ResponseEntity<QuizAttempt> generateQuizAttempt(@PathVariable Long quizId , @PathVariable Long studentId) {
        QuizAttempt quizAttempt = quizService.generateQuizAttempt(quizId , studentId);
        return ResponseEntity.ok(quizAttempt);
    }
}
