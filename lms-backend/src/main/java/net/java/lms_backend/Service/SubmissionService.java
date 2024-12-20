package net.java.lms_backend.Service;

import lombok.Getter;
import lombok.Setter;
import net.java.lms_backend.Repositrory.*;
import net.java.lms_backend.entity.Assignment;
import net.java.lms_backend.entity.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Getter
@Setter
@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Submission createSubmission(Long assignmentId, Submission submission) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        submission.setAssignment(assignment);
        return submissionRepository.save(submission);
    }


}
