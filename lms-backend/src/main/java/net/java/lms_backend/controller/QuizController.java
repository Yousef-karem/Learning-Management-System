package net.java.lms_backend.controller;

import net.java.lms_backend.Service.QuizService;
import net.java.lms_backend.dto.QuizDTO;
import net.java.lms_backend.entity.Quiz;
import net.java.lms_backend.entity.QuizAttempt;
import net.java.lms_backend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/instructor/quiz/create/{courseId}")
    public ResponseEntity<Quiz> createQuiz(@PathVariable Long courseId, @RequestBody QuizDTO quizDTO) {
        Quiz quiz = quizService.createQuiz(courseId, quizDTO);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/student/attempt/{quizId}")
    public ResponseEntity<QuizAttempt> generateQuizAttempt(@AuthenticationPrincipal User user, @PathVariable Long quizId) {
        QuizAttempt quizAttempt = quizService.generateQuizAttempt(quizId , user.getId());
        return ResponseEntity.ok(quizAttempt);
    }

    @PatchMapping("/student/attempt/{quizAttemptId}")
    public ResponseEntity<Integer> updateQuizAttempt(
            @PathVariable Long quizAttemptId,
            @RequestBody Map<Long, String> answers) {
        int score = quizService.updateQuizAttempt(quizAttemptId, answers);
        return ResponseEntity.ok(score);
    }

    @GetMapping("/student/attempt/{quizAttemptId}")
    public ResponseEntity<QuizAttempt> getQuizAttempt(@PathVariable Long quizAttemptId) {
        QuizAttempt quizAttempt = quizService.getQuizAttempt(quizAttemptId);
        return ResponseEntity.ok(quizAttempt);
    }

    @GetMapping("/student/performance/{quizId}")
    public ResponseEntity<Integer> getQuizPerformance(@PathVariable Long quizId) {
        double performance = quizService.getAverageScoreByQuizId(quizId);
        return ResponseEntity.ok((int) performance);
    }

    @GetMapping("/student/course/{courseId}")
    public List<QuizAttempt> getQuizAttemptsByStudent( @PathVariable Long courseId,@AuthenticationPrincipal User user) {
        return quizService.getQuizAttemptsByStudent(user.getId(),courseId);
    }

    @GetMapping("/student/course/{courseId}/averageScore")
    public Double getAverageScoreByStudent( @PathVariable Long courseId,@AuthenticationPrincipal User user) {
        return quizService.getAverageScoreOfStudent(user.getId(), courseId);
    }

}
