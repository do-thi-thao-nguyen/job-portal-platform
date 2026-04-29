package com.jobportal.backend.controller;

import com.jobportal.backend.entity.Notification;
import com.jobportal.backend.entity.User;
import com.jobportal.backend.repository.NotificationRepository;
import com.jobportal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;


@RestController
@RequestMapping("/api/notifications")
@PreAuthorize("hasAnyRole('USER','EMPLOYER')")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Notification> getMyNotifications() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return notificationRepository.findByUser(user);
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (!n.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        n.setRead(true);

        return notificationRepository.save(n);
    }
}