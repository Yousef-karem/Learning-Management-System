package net.java.lms_backend.Repositrory;

import net.java.lms_backend.entity.Notification;
import net.java.lms_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndRead(User user, boolean read);
    List<Notification> findByUser(User user);
    Optional<Notification> findById(Long notificationId);
}