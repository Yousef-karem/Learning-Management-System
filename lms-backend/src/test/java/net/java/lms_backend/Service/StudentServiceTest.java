package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.*;
import net.java.lms_backend.dto.StudentDTO;
import net.java.lms_backend.entity.Role;
import net.java.lms_backend.entity.User;
import net.java.lms_backend.mapper.StudentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private LessonRepositery lessonRepo;

    @Mock
    private EnrollmentRepo enrollmentRepo;

    @Mock
    private AttendanceRepo attendanceRepo;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        User student1 = new User(1L, "student1", "student1@example.com", Role.STUDENT);
        User student2 = new User(2L, "student2", "student2@example.com", Role.STUDENT);
        when(userRepo.findByRole(Role.STUDENT)).thenReturn(Arrays.asList(student1, student2));

        List<StudentDTO> students = studentService.getAllStudents();

        assertEquals(2, students.size());
        verify(userRepo, times(1)).findByRole(Role.STUDENT);
    }



    @Test
    void testCreateStudent() {
        StudentDTO studentDTO = new StudentDTO("student1", "student1@example.com");
        User student = new User(null, "student1", "student1@example.com", Role.STUDENT);
        User savedStudent = new User(1L, "student1", "student1@example.com", Role.STUDENT);

        when(userRepo.save(any(User.class))).thenReturn(savedStudent);

        StudentDTO result = studentService.createStudent(studentDTO);

        assertNotNull(result);
        assertEquals("student1", result.getUsername());
        verify(userRepo, times(1)).save(any(User.class));
    }


    @Test
    void testDeleteStudent() {
        when(userRepo.existsById(1L)).thenReturn(true);

        boolean result = studentService.deleteStudent(1L);

        assertTrue(result);
        verify(userRepo, times(1)).existsById(1L);
        verify(userRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteStudent_NotFound() {
        when(userRepo.existsById(1L)).thenReturn(false);

        boolean result = studentService.deleteStudent(1L);

        assertFalse(result);
        verify(userRepo, times(1)).existsById(1L);
        verify(userRepo, never()).deleteById(1L);
    }
}