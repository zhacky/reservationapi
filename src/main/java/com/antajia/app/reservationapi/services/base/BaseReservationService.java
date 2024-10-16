package com.antajia.app.reservationapi.services.base;

import com.antajia.app.reservationapi.dtos.ReservationDto;
import com.antajia.app.reservationapi.models.Reservation;

import java.util.List;
import java.util.Optional;

/**
 * Interface for basic reservation service operations such as create, update, delete, and retrieve reservations.
 */
public interface BaseReservationService {

    /**
     * Retrieves a list of all reservations.
     *
     * @return a list of {@link ReservationDto} containing details of all reservations
     */
    List<ReservationDto> getAllReservations();

    /**
     * Retrieves a reservation by its unique ID.
     *
     * @param id the unique ID of the reservation
     * @return an {@link Optional} containing the {@link ReservationDto} if found, or empty if not found
     */
    Optional<ReservationDto> getReservationById(Long id);

    /**
     * Creates a new reservation.
     *
     * @param reservationDto the reservation data to create
     * @return the newly created {@link ReservationDto}
     */
    ReservationDto createReservation(ReservationDto reservationDto);

    /**
     * Updates an existing reservation with new details.
     *
     * @param id the unique ID of the reservation to update
     * @param updatedReservationDto the updated reservation data
     * @return an {@link Optional} containing the updated {@link ReservationDto}, or empty if the reservation is not found
     */
    Optional<ReservationDto> updateReservation(Long id, ReservationDto updatedReservationDto);

    /**
     * Deletes a reservation by its unique ID.
     *
     * @param id the unique ID of the reservation to delete
     * @return true if the reservation was successfully deleted, false otherwise
     */
    boolean deleteReservation(Long id);

    ReservationDto convertToDto(Reservation reservation);

    Reservation convertToEntity(ReservationDto dto);
}
