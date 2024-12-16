package net.java.lms_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

   /*@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons;
*/

    public Course(long id, String title, String description, String duration, List<String> mediaFiles, User user, Instructor instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.mediaFiles = mediaFiles;
        this.user = user;
        this.instructor = instructor;
    }

    public long getId() {
        return id;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public List<String> getMediaFiles() {
        return mediaFiles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setMediaFiles(List<String> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }


    public Course(){

}


}
