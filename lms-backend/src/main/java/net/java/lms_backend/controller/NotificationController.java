package net.java.lms_backend.controller;

import net.java.lms_backend.entity.Notification;
import net.java.lms_backend.entity.User;
import net.java.lms_backend.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping
    public List<Notification> getNotifications(@AuthenticationPrincipal User user,
                                               @RequestParam(defaultValue = "true") boolean onlyUnread) {
        return notificationService.getNotifications(user, onlyUnread);
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }
}