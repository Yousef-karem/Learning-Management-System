package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.EnrollmentRepo;
import net.java.lms_backend.entity.*;
import net.java.lms_backend.Repositrory.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EnrollmentRepo enrollmentRepo;
    private final EmailService emailService;

    public NotificationService(NotificationRepository notificationRepository, EnrollmentRepo enrollmentRepo, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.enrollmentRepo = enrollmentRepo;
        this.emailService = emailService;
    }

    public List<Notification> getNotifications(User user, boolean onlyUnread) {
        if (onlyUnread) {
            return notificationRepository.findByUserAndRead(user, false);
        } else {
            return notificationRepository.findByUser(user);
        }
    }
    public void notify(User user, String message) {
        Notification notification = new Notification(message, user);
        notificationRepository.save(notification);
        Email email = new Email(message, EmailType.Notification,user);
        emailService.send(email);
    }

    public void markAsRead(Long id)
    {
        Optional <Notification>notification = notificationRepository.findById(id);
        notification.get().setRead(true);
        notificationRepository.save(notification.get());
    }
    public void notifyAll(Long courseId,String message)
    {
        List<Enrollment>enrollments=enrollmentRepo.findByCourseId(courseId);
        for(Enrollment enrollment:enrollments)
        {
            boolean confirmed=enrollment.getConfirmed();
            if(!confirmed)
                continue;
            notify(enrollment.getStudent(),message);
        }
    }
}