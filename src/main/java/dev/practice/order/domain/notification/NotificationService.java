package dev.practice.order.domain.notification;

public interface NotificationService {
    void sendEmail(String email, String title, String description);
    void sendKakao(String phoneNo, String description);
    void sendSns(String phoneNo, String description);
}
