package net.java.lms_backend.dto;

import java.time.LocalDateTime;

public class SubmissionDTO {
    private Long id;
    private LocalDateTime submittedAt;
    private Long studentId;
    private Long assignmentId;
    private String fileUrl;
    public SubmissionDTO(Long id, LocalDateTime submittedAt, Long studentId, Long assignmentId, String fileUrl) {
        this.id = id;
        this.submittedAt = submittedAt;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.fileUrl = fileUrl;
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
    public String getFileUrl() {
        return fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
