package net.java.lms_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.java.lms_backend.entity.Lesson;
import net.java.lms_backend.entity.MediaFiles;
import net.java.lms_backend.entity.User;
import java.util.List;

@AllArgsConstructor
public class Coursedto {
    private Long id;
    private String title;
    private String description;
    private String duration;
    private User instructor;
    private List<Lesson> lessons;
    private List<Long> lessonIds;
    private List<MediaFiles> mediaFiles;
    private List<QuestionDTO> questionsBank;


    public Coursedto(Long id,
                     String title,
                     String description,
                     String duration,
                     User instructor,
                     List<MediaFiles> mediaFiles) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.mediaFiles = mediaFiles;
        this.instructor = instructor;
//        this.userId = userid;

    }

    public Coursedto(Long id, String title, String description, String duration, List<MediaFiles> mediaFiles, Long id2, List<Long> lessonIds) {
    }
    public Coursedto() {
    }


//    public User getUser() {
//        return user;
//    }
    public User getInstructor() {
        return instructor;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public List<MediaFiles> getMediaFiles() {
        return mediaFiles;
    }

    public String getDuration() {
        return duration;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMediaFiles(List<MediaFiles> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public void setLessonIds(List<Long> lessonIds) {
        this.lessonIds = lessonIds;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }
    //    public Long getUserId() {
//        return userId;
//    }


    public List<QuestionDTO> getQuestionsBank() {
        return questionsBank;
    }

    public void setQuestionsBank(List<QuestionDTO> questionsBank) {
        this.questionsBank = questionsBank;
    }


}