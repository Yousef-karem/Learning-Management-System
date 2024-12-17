package net.java.lms_backend.mapper;

import net.java.lms_backend.dto.Coursedto;
import net.java.lms_backend.entity.Course;
import net.java.lms_backend.entity.Instructor;
import net.java.lms_backend.entity.User;

public class CourseMapper {
    public static Coursedto mapToCoursedto(Course course){
        return new Coursedto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getDuration(),
                course.getMediaFiles(),
                course.getUser().getId(),
                course.getInstructor().getId()


                );


    }
    public static Course maptoCourse(Coursedto coursedto) {
        Course course = new Course();
        course.setId(coursedto.getId());
        course.setTitle(coursedto.getTitle());
        course.setDescription(coursedto.getDescription());
        course.setDuration(coursedto.getDuration());
        course.setMediaFiles(coursedto.getMediaFiles());
        course.setUser(coursedto.getUser());
        return course;
    }

}
