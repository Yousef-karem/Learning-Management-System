package net.java.lms_backend.entity;
import jakarta.persistence.*;

import java.util.Map;

@Entity
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private Student student;

    @ElementCollection
    private Map<Long, String> answers; // Question ID to user answer

    private int score;

    @OneToOne(mappedBy = "quizAttempt", cascade = CascadeType.ALL)
    private Feedback feedback;
}
