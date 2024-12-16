package net.java.lms_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Student extends User{
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
