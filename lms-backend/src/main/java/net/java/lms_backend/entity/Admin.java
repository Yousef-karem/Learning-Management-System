package net.java.lms_backend.entity;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User{
    public Admin(String username, String password, String email)
    {
        this.role=0;
        this.username=username;
        this.password=password;
        this.email=email;
    }
    public Admin()
    {
        this.role=0;
    }
}
