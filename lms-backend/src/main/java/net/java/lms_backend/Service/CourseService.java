package net.java.lms_backend.Service;

import lombok.Getter;
import lombok.Setter;
import net.java.lms_backend.Repositrory.CourseRepository;
import net.java.lms_backend.Repositrory.InstructorRepository;
import net.java.lms_backend.Repositrory.UserRepository;
import net.java.lms_backend.dto.Coursedto;
import net.java.lms_backend.entity.Course;
import net.java.lms_backend.entity.Instructor;
import net.java.lms_backend.entity.User;
import net.java.lms_backend.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class CourseService {
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final InstructorRepository instructorRepo;

    public CourseService(CourseRepository courseRepo, UserRepository userRepo, InstructorRepository instructorRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        this.instructorRepo = instructorRepo;
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
    public List<Course> getCoursesByInstructor(Long instructorId){
        return courseRepo.findByInstructorId(instructorId);
    }
    public void deleteCourse(Long CourseId){

        courseRepo.deleteById(CourseId);
    }
    public Coursedto getCourseById(Long id) {
        Course course=courseRepo.findById(id).orElseThrow(() -> new RuntimeException("Course not found with id " + id));
        return CourseMapper.mapToCoursedto(course);


    }
}
