package net.java.lms_backend.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    int role;
    @Column(unique=true,nullable=false)
    String username;
    @Column
    String password;
    @Column(unique=true)
    String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses=new ArrayList<Course>();


    public Long getId() {
        return id;
    }
}
