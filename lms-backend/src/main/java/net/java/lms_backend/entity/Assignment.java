package net.java.lms_backend.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Assignment extends Assessment {
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<Submission> submissions;
}

