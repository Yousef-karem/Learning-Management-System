package net.java.lms_backend.entity;

import jakarta.persistence.*;

@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    @Column(length =1000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
