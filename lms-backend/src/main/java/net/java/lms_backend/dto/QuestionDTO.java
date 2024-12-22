package net.java.lms_backend.dto;


import net.java.lms_backend.entity.QuestionType;

import java.util.ArrayList;
import java.util.List;


public class QuestionDTO {
    private Long id;
    private String content;
    private String correctAnswer;
    private List<String> options = new ArrayList<>();
    private Long courseId;
    private QuestionType type;

    public QuestionDTO() {}

    public QuestionDTO(Long id, String content, String correctAnswer, List<String> options, Long aLong, QuestionType type) {
        this.id = id;
        this.content = content;
        this.correctAnswer = correctAnswer;
        this.options = options;
        this.courseId = aLong;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public QuestionType getType() {
        return type;
    }

//    public QuestionType getType() {
//        return this.type;
//    }

}
