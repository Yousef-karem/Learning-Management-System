package net.java.lms_backend.Repositrory;

import net.java.lms_backend.entity.Enrollment;
import net.java.lms_backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
}
