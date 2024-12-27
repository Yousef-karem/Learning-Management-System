package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.EnrollmentRepo;
import net.java.lms_backend.entity.Enrollment;
import net.java.lms_backend.entity.Notification;
import net.java.lms_backend.entity.User;
import net.java.lms_backend.Repositrory.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    private EnrollmentRepo enrollmentRepo;
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
    }

    public void markAsRead(Notification notification) {
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void createNotification(String message, User user) {
        Notification notification = new Notification(message, user);
        notificationRepository.save(notification);
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
            Notification notification=new Notification(message,enrollment.getStudent());
            notificationRepository.save(notification);
        }
    }
}