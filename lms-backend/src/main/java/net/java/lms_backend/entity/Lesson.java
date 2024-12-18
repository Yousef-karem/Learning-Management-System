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
    /*@ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;*/

    public void setCourse(Course course) {
        this.course=course;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }
}
