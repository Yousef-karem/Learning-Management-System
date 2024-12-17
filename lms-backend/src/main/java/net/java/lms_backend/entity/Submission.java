package net.java.lms_backend.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL)
    private Feedback feedback;

    private Double grade; // Grading by instructor
}

