package net.java.lms_backend.controller;

import net.java.lms_backend.Repositrory.PerformanceRepo;
import net.java.lms_backend.Service.CourseService;
import net.java.lms_backend.dto.*;
import net.java.lms_backend.entity.Course;
import net.java.lms_backend.entity.Lesson;
import net.java.lms_backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private PerformanceRepo performanceRepo;

    private CourseController courseController;
    private Coursedto coursedto;
    private LessonDTO lessonDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseController = new CourseController(courseService, performanceRepo);

        coursedto = new Coursedto();
        coursedto.setId(1L);
        coursedto.setTitle("Test Course");

        lessonDTO = new LessonDTO();
        lessonDTO.setTitle("Test Lesson");
        lessonDTO.setContent("Test Content");
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void addLessonToCourse() {
        // Create argument captor for Lesson
        ArgumentCaptor<Lesson> lessonCaptor = ArgumentCaptor.forClass(Lesson.class);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        // Create the lesson
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());

        when(courseService.addLessonToCourse(any(Long.class), any(Lesson.class))).thenReturn(lesson);

        ResponseEntity<Lesson> response = courseController.addLessonToCourse(1L, lessonDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(lesson, response.getBody());

        // Verify and capture arguments
        verify(courseService).addLessonToCourse(idCaptor.capture(), lessonCaptor.capture());

        // Assert the captured values
        assertEquals(1L, idCaptor.getValue());
        assertEquals(lessonDTO.getTitle(), lessonCaptor.getValue().getTitle());
        assertEquals(lessonDTO.getContent(), lessonCaptor.getValue().getContent());
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void viewAllCourse() {
        List<Coursedto> courses = Arrays.asList(coursedto);
        when(courseService.ViewAllCourse()).thenReturn(courses);

        ResponseEntity<List<Coursedto>> response = courseController.getAvailableCourses();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(courseService).ViewAllCourse();
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getCoursesByInstructor() {
        List<Coursedto> courses = Arrays.asList(coursedto);
        when(courseService.getCoursesByInstructor(1L)).thenReturn(courses);

        ResponseEntity<List<Coursedto>> response = courseController.getCoursesByInstructor(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(courses, response.getBody());
        verify(courseService).getCoursesByInstructor(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getCourseById() {
        when(courseService.getCourseById(1L)).thenReturn(coursedto);

        ResponseEntity<Coursedto> response = courseController.getCourseById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(coursedto, response.getBody());
        verify(courseService).getCourseById(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void deleteCourse() {
        ResponseEntity<Void> response = courseController.deleteCourse(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService).deleteCourse(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void uploadMediaFiles() {
        // Create a list of MultipartFile instead of MockMultipartFile
        List<MultipartFile> files = Arrays.asList(
                new MockMultipartFile(
                        "file",
                        "test.pdf",
                        "application/pdf",
                        "test content".getBytes()
                )
        );

        ResponseEntity<Void> response = courseController.uploadMediaFiles(1L, files);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService).uploadMediaFiles(eq(1L), any(List.class));
    }


    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getEnrolledStudents() {
        List<StudentDTO> students = Arrays.asList(new StudentDTO());
        when(courseService.getEnrolledStudents(1L)).thenReturn(students);

        ResponseEntity<List<StudentDTO>> response = courseController.getEnrolledStudents(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
        verify(courseService).getEnrolledStudents(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void generateOtp() {
        when(courseService.generateOtp(1L)).thenReturn("123456");

        ResponseEntity<String> response = courseController.generateOtp(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("123456", response.getBody());
        verify(courseService).generateOtp(1L);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void validateOtp() {
        when(courseService.validateOtp(1L, "123456", 1L)).thenReturn(true);

        ResponseEntity<Boolean> response = courseController.validateOtp(1L, "123456", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(courseService).validateOtp(1L, "123456", 1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getPerformance() {
        when(courseService.getPerformanceForStudent(1L, 1L)).thenReturn(85);

        ResponseEntity<Integer> response = courseController.getPerformance(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(85, response.getBody());
        verify(courseService).getPerformanceForStudent(1L, 1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getAttendanceForLesson() {
        List<Attendancedto> attendance = Arrays.asList(new Attendancedto());
        when(courseService.getAttendanceForLesson(1L)).thenReturn(attendance);

        ResponseEntity<List<Attendancedto>> response = courseController.getAttendanceForLesson(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(attendance, response.getBody());
        verify(courseService).getAttendanceForLesson(1L);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void addQuestionsToCourse() {
        List<QuestionDTO> questions = Arrays.asList(new QuestionDTO());

        ResponseEntity<Void> response = courseController.addQuestionsToCourse(1L, questions);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(courseService).addQuestionsToCourse(1L, questions);
    }

    @Test
    @WithMockUser(roles = "INSTRUCTOR")
    void getQuestionsByCourseId() {
        List<QuestionDTO> questions = Arrays.asList(new QuestionDTO());
        when(courseService.getQuestionsByCourseId(1L)).thenReturn(questions);

        ResponseEntity<List<QuestionDTO>> response = courseController.getQuestionsByCourseId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questions, response.getBody());
        verify(courseService).getQuestionsByCourseId(1L);
    }
}