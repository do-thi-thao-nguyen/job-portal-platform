package com.jobportal.backend.repository;
import com.jobportal.backend.entity.Notification;
import com.jobportal.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);

    long countByUserAndReadFalse(User user);
}
