package net.java.lms_backend.mapper;

import net.java.lms_backend.dto.StudentDTO;
import net.java.lms_backend.entity.User;

public class StudentMapper {
    public static StudentDTO mapToStudentDTO(User student) {
        if (student == null) {
            return null;
        }
        return new StudentDTO(student.getId(), student.getUsername(), student.getEmail());
    }

    public static User mapToStudent(StudentDTO studentDTO) {
        if (studentDTO == null) {
            return null;
        }
        User student = new User();
        student.setId(studentDTO.getId());
        student.setUsername(studentDTO.getUsername());
        student.setEmail(studentDTO.getEmail());
        return student;
    }
}
