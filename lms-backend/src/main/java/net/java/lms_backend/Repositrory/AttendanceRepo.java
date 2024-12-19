package net.java.lms_backend.Repositrory;

import net.java.lms_backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<Attendance,Long> {
    Attendance findByLessonIdAndOtp(Long lessonId, String otp);
}
