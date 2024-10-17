package com.antajia.app.reservationapi.services;

import com.antajia.app.reservationapi.models.ContactMethod;
import com.antajia.app.reservationapi.models.Reservation;
import com.antajia.app.reservationapi.services.base.BaseNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService implements BaseNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final List<String> pendingNotifications = new ArrayList<>();

    @Override
    public void sendNotification(Reservation reservation, String message) {
        ContactMethod preferredMethod = getPreferredContactMethod(reservation);
                    switch (preferredMethod.getName()) {
                        case "Email":
                            sendEmailNotification(reservation, message);
                        case "SMS":
                            sendSmsNotification(reservation, message);
                        default:
                            logger.warn("Unknown contact method for reservation: {}", reservation.getId());
                    }
    }

    @Override
    public void sendEmailNotification(Reservation reservation, String message) {
        // Implement email sending logic here
        logger.info("Sending email notification to: {}", reservation.getEmail());
        logNotification(reservation, message, true);
    }

    @Override
    public void sendSmsNotification(Reservation reservation, String message) {
        // Implement SMS sending logic here
        logger.info("Sending SMS notification to: {}", reservation.getPhoneNumber());
        logNotification(reservation, message, true);
    }

    @Override
    public ContactMethod getPreferredContactMethod(Reservation reservation) {
        // For simplicity, we'll just return the first contact method
        return reservation.getContactMethods().iterator().next();
    }

    @Override
    public String formatNotificationMessage(Reservation reservation) {
        return String.format("Reservation confirmed for %s on %s at %s for %d guests.",
                reservation.getName(),
                reservation.getReservationDate(),
                reservation.getReservationTime(),
                reservation.getNumberOfGuests());
    }

    @Override
    public void handleNotificationFailure(Reservation reservation, String failureReason) {
        logger.error("Failed to send notification for reservation: {}. Reason: {}", reservation.getId(), failureReason);
        logNotification(reservation, failureReason, false);
    }

    @Override
    public void logNotification(Reservation reservation, String message, boolean success) {
        String logMessage = String.format("Notification for reservation %d: %s. Success: %b",
                reservation.getId(), message, success);
        if (success) {
            logger.info(logMessage);
        } else {
            logger.error(logMessage);
        }
    }

    @Override
    public void scheduleNotification(Reservation reservation, String message, long delayInMillis) {
        // Implement scheduling logic here
        logger.info("Scheduling notification for reservation: {} with delay: {} ms", reservation.getId(), delayInMillis);
        pendingNotifications.add(String.format("Scheduled: %s", message));
    }

    @Override
    public List<String> getPendingNotifications() {
        return new ArrayList<>(pendingNotifications);
    }
}
