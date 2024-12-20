package net.java.lms_backend.Repositrory;

import net.java.lms_backend.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
}
