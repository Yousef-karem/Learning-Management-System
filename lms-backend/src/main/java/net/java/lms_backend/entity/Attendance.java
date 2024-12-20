package net.java.lms_backend.entity;

import jakarta.persistence.*;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private String otp;
    private boolean active;

    public void setLesson(Lesson lesson) {
        this.lesson=lesson;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }
}
