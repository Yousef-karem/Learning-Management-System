package net.java.lms_backend.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;




@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType type; // MCQ, TRUE_FALSE, SHORT_ANSWER

    @ElementCollection
    private List<String> options = new ArrayList<>(); // For MCQ

    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Question() {}

    public Question(String content, QuestionType type, List<String> options, String correctAnswer, Course course) {
        this.content = content;
        this.type = type;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.course = course;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

