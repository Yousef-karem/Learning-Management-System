package net.java.lms_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String duration;

    @ElementCollection
    private List<String> mediaFiles;

    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false) // Foreign key in the course table
    private Instructor instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons;


    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private QuestionBank questionBank;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Assessment> assessments;

}
