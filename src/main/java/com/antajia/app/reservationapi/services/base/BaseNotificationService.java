package com.antajia.app.reservationapi.services.base;

import com.antajia.app.reservationapi.models.ContactMethod;
import com.antajia.app.reservationapi.models.Reservation;

import java.util.List;

public interface BaseNotificationService {

    void sendNotification(Reservation reservation, String message);

    void sendEmailNotification(Reservation reservation, String message);

    void sendSmsNotification(Reservation reservation, String message);

    void sendPhoneNotification(Reservation reservation, String message);

    ContactMethod getPreferredContactMethod(Reservation reservation);

    String formatNotificationMessage(Reservation reservation);

    void handleNotificationFailure(Reservation reservation, String failureReason);

    void logNotification(Reservation reservation, String message, boolean success);

    void scheduleNotification(Reservation reservation, String message, long delayInMillis);

    List<String> getPendingNotifications();
}
