package net.java.lms_backend.Service;

import lombok.Getter;
import lombok.Setter;
import net.java.lms_backend.Repositrory.*;
import net.java.lms_backend.dto.SubmissionDTO;
import net.java.lms_backend.entity.Assignment;
import net.java.lms_backend.entity.Submission;
import net.java.lms_backend.mapper.SubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    private final SubmissionMapper submissionMapper;

    public SubmissionService(SubmissionRepository submissionRepository,
                             StudentRepository studentRepository,
                             AssignmentRepository assignmentRepository,
                             SubmissionMapper submissionMapper) {
        this.submissionRepository = submissionRepository;
        this.studentRepository = studentRepository;
        this.assignmentRepository = assignmentRepository;
        this.submissionMapper = submissionMapper;
    }

    public SubmissionDTO createSubmission(Long assignmentId, Long studentId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty.");
        }

        // Save the file locally
        String fileName = saveFile(file);

        Submission submission = new Submission();
        submission.setFileName(fileName);
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found")));
        submission.setAssignment(assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found")));

        Submission savedSubmission = submissionRepository.save(submission);
        return new SubmissionDTO(savedSubmission.getId(), savedSubmission.getSubmittedAt(), studentId, assignmentId, fileName);
    }

    private String saveFile(MultipartFile file) {
        try {
            // Define the directory where files will be saved
            String uploadDir = "uploads/";
            String fileName = file.getOriginalFilename();

            // Ensure the directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not save file. Error: " + e.getMessage());
        }
    }

    public List<SubmissionDTO> getAllSubmissions() {
        return submissionRepository.findAll().stream()
                .map(submissionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SubmissionDTO getSubmissionById(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found"));
        return submissionMapper.toDTO(submission);
    }

    public void deleteSubmission(Long id) {
        if (!submissionRepository.existsById(id)) {
            throw new IllegalArgumentException("Submission not found");
        }
        submissionRepository.deleteById(id);
    }

}
