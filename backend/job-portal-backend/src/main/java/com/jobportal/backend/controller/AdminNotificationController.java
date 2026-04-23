package com.jobportal.backend.controller;
import com.jobportal.backend.entity.Notification;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.NotificationRepository;
import com.jobportal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;    

@RestController
@RequestMapping("/admin/notifications")
@PreAuthorize("hasRole('ADMIN')")
public class AdminNotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/{userId}")
    public Notification send(@PathVariable Long userId,
                             @RequestBody Notification req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification n = new Notification();
        n.setTitle(req.getTitle());
        n.setContent(req.getContent());
        n.setUser(user);

        return notificationRepository.save(n);
    }
}