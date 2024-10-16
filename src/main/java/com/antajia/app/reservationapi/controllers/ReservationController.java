package com.antajia.app.reservationapi.controllers;

import com.antajia.app.reservationapi.dtos.ReservationDto;
import com.antajia.app.reservationapi.models.Reservation;
import com.antajia.app.reservationapi.services.NotificationService;
import com.antajia.app.reservationapi.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing reservation operations.
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final NotificationService notificationService;

    /**
     * Constructor to inject the ReservationService dependency.
     *
     * @param reservationService  the service layer for reservation operations
     * @param notificationService the service for notification operations
     */
    @Autowired
    public ReservationController(ReservationService reservationService, NotificationService notificationService) {
        this.reservationService = reservationService;
        this.notificationService = notificationService;
    }

    /**
     * Retrieves all reservations.
     *
     * @return a list of all reservations
     */
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param id the ID of the reservation
     * @return the reservation with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new reservation.
     *
     * @param reservationDto the reservation data to create
     * @return the newly created reservation
     */
    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationDto createdReservation = reservationService.createReservation(reservationDto);
        Reservation reservation = reservationService.convertToEntity(createdReservation);
        String message = notificationService.formatNotificationMessage(reservation);
        notificationService.sendNotification(reservation, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    /**
     * Updates an existing reservation.
     *
     * @param id             the ID of the reservation to update
     * @param reservationDto the updated reservation data
     * @return the updated reservation
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        Optional<ReservationDto> updatedReservationDto = reservationService.updateReservation(id, reservationDto);

        if (updatedReservationDto.isPresent()){
            Reservation updatedReservation = reservationService.convertToEntity(updatedReservationDto.get());
        String message = "Your reservation has been updated: " + notificationService.formatNotificationMessage(updatedReservation);
        notificationService.sendNotification(updatedReservation, message);
        }
        return updatedReservationDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param id the ID of the reservation to delete
     * @return a response indicating the deletion status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (reservationService.deleteReservation(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
