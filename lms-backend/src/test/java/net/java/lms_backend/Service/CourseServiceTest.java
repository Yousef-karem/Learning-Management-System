package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.*;
import net.java.lms_backend.dto.Coursedto;
import net.java.lms_backend.dto.StudentDTO;
import net.java.lms_backend.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;


class CourseServiceTest {
    @InjectMocks
    private CourseService courseService;
    @Mock
    private CourseRepository courseRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private LessonRepositery lessonRepo;;
    @Mock
    private EnrollmentRepo enrollmentRepo;
    @Mock
    private AttendanceRepo attendanceRepo;
    @Mock
    private PerformanceRepo performanceRepo;
    @Mock
    private User userMock;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void viewAllCourse() {
        List<Course> courses=new ArrayList<>();
        MediaFiles mediaFile1 = new MediaFiles(1L, "file1.pdf");
        MediaFiles mediaFile2 = new MediaFiles(2L, "file2.doc");
        User instructor = new User();
        instructor.setId(1L);
        courses.add(new Course(
                1L,
                "Advanced SW",
                "course on Sw",
                "6 weeks",
                List.of(mediaFile1,mediaFile2),
                instructor

        ));
        when(courseRepo.findAll()).thenReturn(courses);
        List<Coursedto> result = courseService.ViewAllCourse();
        assertEquals(courses.size(), result.size());
        assertEquals(courses.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(courses.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(courses.get(0).getDuration(), result.get(0).getDuration());
        assertEquals(courses.get(0).getMediaFiles().get(0).getFileName(), result.get(0).getMediaFiles().get(0).getFileName());
        assertEquals(courses.get(0).getMediaFiles().get(1).getFileName(), result.get(0).getMediaFiles().get(1).getFileName());
        verify(courseRepo, times(1)).findAll();


    }

    @Test
    void getCoursesByInstructor() {
        Long instructorId = 1L;
        MediaFiles mediaFile1 = new MediaFiles(1L, "file1.pdf");
        MediaFiles mediaFile2 = new MediaFiles(2L, "file2.doc");
        User instructor = new User();
        instructor.setId(1L);
        List<Course> courses=new ArrayList<>();
        courses.add(new Course(
                1L,
                "Advanced SW",
                "course on Sw",
                "6 weeks",
                List.of(mediaFile1,mediaFile2),
                instructor

        ));
        when(courseRepo.findByInstructorId(instructorId)).thenReturn(courses);
        List<Coursedto> result = courseService.getCoursesByInstructor(instructorId);
        assertEquals(courses.size(), result.size());
        assertEquals(courses.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(courses.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(courses.get(0).getDuration(), result.get(0).getDuration());
        assertEquals(courses.get(0).getMediaFiles().get(0).getFileName(), result.get(0).getMediaFiles().get(0).getFileName());
        assertEquals(courses.get(0).getMediaFiles().get(1).getFileName(), result.get(0).getMediaFiles().get(1).getFileName());
        verify(courseRepo, times(1)).findByInstructorId(instructorId);


    }
    @Test
    void getCourseById() {
        Long courseId=1L;
        MediaFiles mediaFile1 = new MediaFiles(1L, "file1.pdf");
        MediaFiles mediaFile2 = new MediaFiles(2L, "file2.doc");
        User instructor = new User();
        instructor.setId(1L);
        Course course=new Course(
                1L,
                "Advanced SW",
                "course on Sw",
                "6 weeks",
                List.of(mediaFile1,mediaFile2),
                instructor
        );
        when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));
        Coursedto result = courseService.getCourseById(1L);
        assertEquals(course.getTitle(), result.getTitle());
        assertEquals(course.getDescription(), result.getDescription());
        assertEquals(course.getDuration(), result.getDuration());
        assertEquals(course.getMediaFiles().get(0).getFileName(), result.getMediaFiles().get(0).getFileName());
        assertEquals(course.getMediaFiles().get(1).getFileName(), result.getMediaFiles().get(1).getFileName());
        verify(courseRepo, times(1)).findById(1L);


    }
    @Test
    void deleteCourse() {
        Long courseId = 1L;

        doNothing().when(courseRepo).deleteById(courseId);

        courseService.deleteCourse(courseId);

        verify(courseRepo, times(1)).deleteById(courseId);
    }





    @Test
    void getEnrolledStudents() {
        Long courseId = 1L;
        User student1 = new User();
        student1.setId(1L);
        student1.setUsername("Student 1");

        User student2 = new User();
        student2.setId(2L);
        student2.setUsername("Student 2");
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);

        List<Enrollment> Enrollments = List.of(enrollment1, enrollment2);

        when(enrollmentRepo.findByCourseId(courseId)).thenReturn(Enrollments);

        List<StudentDTO> result = courseService.getEnrolledStudents(courseId);

        assertEquals(Enrollments.size(), result.size());
        assertEquals(Enrollments.get(0).getStudent().getId(), result.get(0).getId());
        assertEquals(Enrollments.get(1).getStudent().getId(), result.get(1).getId());

        verify(enrollmentRepo, times(1)).findByCourseId(courseId);
    }



    @Test
    void validateOtp() {
        Long lessonId = 1L;
        String otp = "10098";
        Long studentId = 1L;
        Course course = new Course();
        course.setId(1L);
        Lesson lesson = new Lesson();
        lesson.setCourse(course);

        Attendance attendance = new Attendance();
        attendance.setOtp(otp);
        attendance.setActive(true);
        attendance.setLesson(lesson);

        when(attendanceRepo.findByLessonIdAndOtp(lessonId, otp)).thenReturn(attendance);

        User student = new User();
        student.setId(studentId);
        when(userRepo.findById(studentId)).thenReturn(Optional.of(student));

        Performance performance = new Performance();
        performance.setTotalLessonsAttended(0);
        when(performanceRepo.findByStudentIdAndCourseId(studentId, course.getId())).thenReturn(performance);

        boolean result = courseService.validateOtp(lessonId, otp, studentId);


        assertTrue(result);
        assertFalse(attendance.isActive());
        assertEquals(student, attendance.getStudent());
        assertEquals(1, performance.getTotalLessonsAttended());


        verify(attendanceRepo, times(1)).save(attendance);
        verify(userRepo, times(1)).findById(studentId);
        verify(performanceRepo, times(1)).save(performance);
    }



}
