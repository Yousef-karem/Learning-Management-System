package net.java.lms_backend.entity;
import jakarta.persistence.*;

import java.util.List;

enum QuestionType {
    MCQ,
    TRUE_FALSE,
    SHORT_ANSWER
}


@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType type; // MCQ, TRUE_FALSE, SHORT_ANSWER

    @ElementCollection
    private List<String> options; // For MCQ

    private String correctAnswer;

    @ManyToOne
    private QuestionBank questionBank;

    @ManyToMany(mappedBy = "questions")
    private List<Quiz> quizzes;
}

