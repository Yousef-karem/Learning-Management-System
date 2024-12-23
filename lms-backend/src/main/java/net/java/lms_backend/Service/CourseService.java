package net.java.lms_backend.Service;

import lombok.Getter;
import lombok.Setter;
import net.java.lms_backend.Repositrory.*;
import net.java.lms_backend.dto.Coursedto;
import net.java.lms_backend.dto.Enrollmentdto;
import net.java.lms_backend.dto.StudentDTO;
import net.java.lms_backend.entity.*;
import net.java.lms_backend.mapper.CourseMapper;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import net.java.lms_backend.mapper.StudentMapper;

@Getter
@Setter
@Service
public class CourseService {
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final InstructorRepository instructorRepo;
    private final LessonRepositery lessonRepo;
    private final StudentRepo studentRepo;
    private final EnrollmentRepo enrollmentRepo;
    private final AttendanceRepo attendanceRepo;

    public CourseService(CourseRepository courseRepo, UserRepository userRepo, InstructorRepository instructorRepo,LessonRepositery lessonRepo,EnrollmentRepo enrollmentRepo,StudentRepo studentRepo,AttendanceRepo attendanceRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        this.instructorRepo = instructorRepo;
        this.lessonRepo=lessonRepo;
        this.enrollmentRepo=enrollmentRepo;
        this.studentRepo=studentRepo;
        this.attendanceRepo=attendanceRepo;
    }

    public Coursedto CreateCourse(Coursedto coursedto) {
//        User user = userRepo.findById(coursedto.getUser ().getId())
//                .orElseThrow(() -> new RuntimeException("User  not found with id: " + coursedto.getUser ().getId()));

        Instructor instructor = instructorRepo.findById(coursedto.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + coursedto.getInstructorId()));

        Course course = new Course();
        course.setTitle(coursedto.getTitle());
        course.setDescription(coursedto.getDescription());
        course.setDuration(coursedto.getDuration());
//        course.setUser (user);
        course.setInstructor(instructor);

        if (coursedto.getMediaFiles() != null) {
            for (MediaFiles mediaFile : coursedto.getMediaFiles()) {
                course.addMediaFile(mediaFile);
            }
        }

        Course savedCourse = courseRepo.save(course);
        return CourseMapper.mapToCoursedto(savedCourse);
    }

    public List<Coursedto> ViewAllCourse() {
        List<Course>courses=courseRepo.findAll();
        return courses.stream().map(course -> CourseMapper.mapToCoursedto(course)).collect(Collectors.toList());

    }

    public List<Coursedto> getCoursesByInstructor(Long instructorId) {
        List<Course> courses = courseRepo.findByInstructorId(instructorId);
        return courses.stream()
                .map(CourseMapper::mapToCoursedto)
                .collect(Collectors.toList());
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
    public void enrollStudentInCourse(Long courseId, Long studentId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        enrollmentRepo.save(enrollment);
    }

    public List<StudentDTO> getEnrolledStudents(Long courseId) {
        List<Enrollment> enrollments = enrollmentRepo.findByCourseId(courseId);
        return enrollments.stream()
                .map(enrollment -> StudentMapper.mapToStudentDTO(enrollment.getStudent()))
                .collect(Collectors.toList());
    }
    public String generateOtp(Long lessonId){
        Lesson lesson=lessonRepo.findById(lessonId).orElseThrow(()->new RuntimeException("Lesson with id not found : " + lessonId));
        String otp = String.valueOf((int)(Math.random() * 9999)+1000000);
        Attendance attendance = new Attendance();
        attendance.setLesson(lesson);
        attendance.setOtp(otp);
        attendance.setActive(true);
        attendanceRepo.save(attendance);
        return otp;

    }
    public boolean validateOtp(Long lessonId, String otp) {
        Attendance attendance = attendanceRepo.findByLessonIdAndOtp(lessonId, otp);
        if (attendance != null && attendance.isActive()) {
            attendance.setActive(false);
            attendanceRepo.save(attendance);
            return true;
        }

        return false;
    }


}