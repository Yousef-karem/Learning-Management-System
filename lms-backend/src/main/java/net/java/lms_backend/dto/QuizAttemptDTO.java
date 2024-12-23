package net.java.lms_backend.dto;

import java.util.Map;

public class QuizAttemptDTO {
    private Long quizId;
    private Long studentId;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
