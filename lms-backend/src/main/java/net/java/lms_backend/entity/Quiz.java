package net.java.lms_backend.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Quiz extends Assessment {
    private boolean randomized;

    @ManyToMany
    @JoinTable(
            name = "quiz_question",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizAttempt> quizAttempts;
}


