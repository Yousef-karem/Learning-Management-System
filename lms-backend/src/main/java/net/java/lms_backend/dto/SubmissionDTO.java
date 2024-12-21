package net.java.lms_backend.dto;

import java.time.LocalDateTime;

public class SubmissionDTO {
    private Long id;
    private LocalDateTime submittedAt = LocalDateTime.now();
    private Long studentId;
    private Long assignmentId;
    private String fileName;
    public SubmissionDTO(Long id, LocalDateTime submittedAt, Long studentId, Long assignmentId, String fileUrl) {
        this.id = id;
        this.submittedAt = submittedAt;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.fileName = fileUrl;
    }
    public SubmissionDTO() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public Long getAssignmentId() {
        return assignmentId;
    }
    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
