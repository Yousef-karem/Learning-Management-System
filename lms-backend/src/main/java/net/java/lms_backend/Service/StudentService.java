package net.java.lms_backend.Service;

import lombok.Getter;
import lombok.Setter;
import net.java.lms_backend.Repositrory.*;
import org.springframework.stereotype.Service;


@Getter
@Setter
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final InstructorRepository instructorRepo;
    private final LessonRepositery lessonRepo;
    private final EnrollmentRepo enrollmentRepo;
    private final AttendanceRepo attendanceRepo;

    public StudentService(CourseRepository courseRepo, UserRepository userRepo, InstructorRepository instructorRepo, LessonRepositery lessonRepo, EnrollmentRepo enrollmentRepo, StudentRepository studentRepository, AttendanceRepo attendanceRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        this.instructorRepo = instructorRepo;
        this.lessonRepo=lessonRepo;
        this.enrollmentRepo=enrollmentRepo;
        this.studentRepository = studentRepository;
        this.attendanceRepo=attendanceRepo;
    }

}
