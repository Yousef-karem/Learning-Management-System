package net.java.lms_backend.controller;

import net.java.lms_backend.Service.StudentService;
import net.java.lms_backend.Service.SubmissionService;
import net.java.lms_backend.dto.SubmissionDTO;
import net.java.lms_backend.entity.Submission;
import net.java.lms_backend.mapper.SubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SubmissionMapper submissionMapper;

    @PostMapping("/submission/{assignmentId}")
    public ResponseEntity<SubmissionDTO> createSubmission(
            @PathVariable Long assignmentId,
            @RequestBody SubmissionDTO submissionDTO
    ) {
        Submission submission = submissionMapper.toEntity(submissionDTO);
        Submission createdSubmission = submissionService.createSubmission(assignmentId, submission);
        return ResponseEntity.status(HttpStatus.CREATED).body(submissionMapper.toDTO(createdSubmission));
    }
}
