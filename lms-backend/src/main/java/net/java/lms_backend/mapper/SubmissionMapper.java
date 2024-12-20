package net.java.lms_backend.mapper;

import net.java.lms_backend.dto.SubmissionDTO;
import net.java.lms_backend.entity.Submission;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {
    public Submission toEntity(SubmissionDTO dto) {
        Submission submission = new Submission();
        submission.setFileUrl(dto.getFileUrl());
        submission.setSubmittedAt(dto.getSubmittedAt());
        return submission;
    }

    public SubmissionDTO toDTO(Submission submission) {
        SubmissionDTO dto = new SubmissionDTO();
        dto.setId(submission.getId());
        dto.setFileUrl(submission.getFileUrl());
        dto.setSubmittedAt(submission.getSubmittedAt());
        dto.setAssignmentId(submission.getAssignment().getId());
        dto.setStudentId(submission.getStudent().getId());
        return dto;
    }
}