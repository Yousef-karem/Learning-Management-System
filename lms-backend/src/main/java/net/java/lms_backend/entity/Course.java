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
    /*@ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;*/
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

}
