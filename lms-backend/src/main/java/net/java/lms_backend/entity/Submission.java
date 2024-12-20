package net.java.lms_backend.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Assignment assignment;

    @ManyToOne
    private Student student;

    private LocalDateTime submittedAt;

    private String fileUrl; // File location

    @Setter
    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL)
    private Feedback feedback;

    private Double grade; // Grading by instructor

    public Submission() {}

    public Submission(Assignment assignment, Student student, LocalDateTime submittedAt, String fileUrl) {
        this.assignment = assignment;
        this.student = student;
        this.submittedAt = submittedAt;
        this.fileUrl = fileUrl;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public long getId() {
        return id;
    }
    public Assignment getAssignment() {
        return assignment;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getFileUrl() {
        return fileUrl;
    }



}

