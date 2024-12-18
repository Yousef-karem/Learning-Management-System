package net.java.lms_backend.Service;

import lombok.Getter;
import lombok.Setter;
import net.java.lms_backend.Repositrory.CourseRepository;
import net.java.lms_backend.Repositrory.InstructorRepository;
import net.java.lms_backend.Repositrory.LessonRepositery;
import net.java.lms_backend.Repositrory.UserRepository;
import net.java.lms_backend.dto.Coursedto;
import net.java.lms_backend.entity.*;
import net.java.lms_backend.mapper.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class CourseService {
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final InstructorRepository instructorRepo;
    private final LessonRepositery lessonRepo;

    public CourseService(CourseRepository courseRepo, UserRepository userRepo, InstructorRepository instructorRepo,LessonRepositery lessonRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        this.instructorRepo = instructorRepo;
        this.lessonRepo=lessonRepo;
    }

    public Coursedto CreateCourse(Coursedto coursedto) {
        User user = userRepo.findById(coursedto.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + coursedto.getUser().getId()));

        Instructor instructor = instructorRepo.findById(coursedto.getInstructor().getId())
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + coursedto.getInstructor().getId()));

        Course course = CourseMapper.maptoCourse(coursedto);

        course.setUser(user);
        course.setInstructor(instructor);

        Course savedCourse = courseRepo.save(course);

        return CourseMapper.mapToCoursedto(savedCourse);
    }

    public List<Coursedto> ViewAllCourse() {
        List<Course>courses=courseRepo.findAll();
        return courses.stream().map(course -> CourseMapper.mapToCoursedto(course)).collect(Collectors.toList());

    }
    public List<Coursedto> getCoursesByInstructor(Long instructorId){
        return courseRepo.findByInstructorId(instructorId);
    }
    public void deleteCourse(Long CourseId){

        courseRepo.deleteById(CourseId);
    }
    public Coursedto getCourseById(Long id) {
        Course course=courseRepo.findById(id).orElseThrow(() -> new RuntimeException("Course not found with id " + id));
        return CourseMapper.mapToCoursedto(course);


    }
    public Lesson addLessonToCourse(Long courseId, Lesson lesson) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        course.addLesson(lesson);
        return lessonRepo.save(lesson);
    }
    public void uploadMediaFiles(Long courseId, List<MultipartFile> files) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        for (MultipartFile file : files) {
            String uploadDir = "C:/uploads/uploads/" + courseId;
            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            try {
                String filePath = uploadDir + "/" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
                MediaFiles mediaFile = new MediaFiles();
                mediaFile.setFileName(file.getOriginalFilename());
                course.addMediaFile(mediaFile);

            } catch (IOException e) {
                throw new RuntimeException("Error saving file: " + file.getOriginalFilename(), e);
            }
        }
        courseRepo.save(course);
    }


}
