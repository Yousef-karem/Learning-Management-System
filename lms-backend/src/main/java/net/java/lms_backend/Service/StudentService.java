package net.java.lms_backend.Service;

import lombok.Getter;
import lombok.Setter;
import net.java.lms_backend.Repositrory.*;
import net.java.lms_backend.dto.StudentDTO;
import net.java.lms_backend.entity.Role;
import net.java.lms_backend.entity.User;
import net.java.lms_backend.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Service
public class StudentService {
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final LessonRepositery lessonRepo;
    private final EnrollmentRepo enrollmentRepo;
    private final AttendanceRepo attendanceRepo;

    public StudentService(CourseRepository courseRepo, UserRepository userRepo,
                          LessonRepositery lessonRepo, EnrollmentRepo enrollmentRepo,  AttendanceRepo attendanceRepo) {
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        this.lessonRepo=lessonRepo;
        this.enrollmentRepo=enrollmentRepo;
        this.attendanceRepo=attendanceRepo;
    }
    

    public List<StudentDTO> getAllStudents() {
        List<User> students = userRepo.findByRole(Role.STUDENT);
        return students.stream()
                .map(StudentMapper::mapToStudentDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        return userRepo.findById(id)
                .map(StudentMapper::mapToStudentDTO)
                .orElse(null);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        User student = StudentMapper.mapToStudent(studentDTO);
        User savedStudent = userRepo.save(student);
        return StudentMapper.mapToStudentDTO(savedStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        return userRepo.findById(id)
                .map(existingStudent -> {
                    existingStudent.setUsername(studentDTO.getUsername());
                    existingStudent.setEmail(studentDTO.getEmail());
                    User updatedStudent = userRepo.save(existingStudent);
                    return StudentMapper.mapToStudentDTO(updatedStudent);
                })
                .orElse(null);
    }

    public boolean deleteStudent(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
