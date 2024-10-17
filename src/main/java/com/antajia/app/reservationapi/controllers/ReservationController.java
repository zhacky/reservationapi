package com.antajia.app.reservationapi.controllers;

import com.antajia.app.reservationapi.dtos.ReservationDto;
import com.antajia.app.reservationapi.models.Reservation;
import com.antajia.app.reservationapi.services.NotificationService;
import com.antajia.app.reservationapi.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all reservations", description = "Returns a list of all reservations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Get reservation by ID", description = "Returns a reservation based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reservation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDto.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Create a new reservation", description = "Creates a new reservation and returns the created reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Update a reservation", description = "Updates an existing reservation based on the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDto.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Delete a reservation", description = "Deletes an existing reservation based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reservation successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Reservation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (reservationService.deleteReservation(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
