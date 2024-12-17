package net.java.lms_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Student extends User{
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<QuizAttempt> quizAttempts;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Submission> submissions;

    public Student(String username, String password, String email)
    {
        this.role=2;
        this.username=username;
        this.password=password;
        this.email=email;
    }
    public Student()
    {
        this.role=2;
    }
}
