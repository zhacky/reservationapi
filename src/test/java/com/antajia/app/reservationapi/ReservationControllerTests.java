package com.antajia.app.reservationapi;

import com.antajia.app.reservationapi.controllers.ReservationController;
import com.antajia.app.reservationapi.dtos.ReservationDto;
import com.antajia.app.reservationapi.services.NotificationService;
import com.antajia.app.reservationapi.services.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    //region GET ALL Test
    @Test
    public void getAllReservations_ShouldReturnReservationsList() throws Exception {

        List<ReservationDto> reservations = List.of(
                new ReservationDto("Dimebag Darrell", "123345667", "dimebagdarrell@metalworld.com", LocalDate.of(2025, 1, 12), LocalTime.of(11, 30), 4, Set.of("SMS")),
                new ReservationDto("Karl Roy", "1234567890", "karl@pot.com", LocalDate.of(2024, 11, 22), LocalTime.of(12, 30), 4, Set.of("SMS")),
                new ReservationDto("James Hetfield", "0987654321", "james@metalworld.com", LocalDate.of(2024, 12, 25), LocalTime.of(10, 30), 2, Set.of("Email"))
        );

        when(reservationService.getAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(reservations)));
    }
    //endregion

    //region GET Test
    @Test
    public void getReservationById_ShouldReturnReservation_WhenExists() throws Exception {

        ReservationDto reservation = new ReservationDto("Mark Daspat", "1234567890", "mark@chemistry.com", LocalDate.of(2024, 12, 22), LocalTime.of(11, 30), 4, Set.of("SMS"));
        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(reservation)));
    }

    @Test
    public void getReservationById_ShouldReturnNotFound_WhenReservationDoesNotExist() throws Exception {

        when(reservationService.getReservationById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    //endregion

    //region POST Test
    @Test
    public void createReservation_ShouldReturnCreatedReservation() throws Exception {
        // Arrange
        ReservationDto reservation = new ReservationDto("Zhack Ariya", "1234567890", "zhacky@test.com", LocalDate.of(2025, 12, 12), LocalTime.of(12, 12), 4, Set.of("SMS"));
        ReservationDto createdReservation = new ReservationDto("Zhack Ariya", "1234567890", "zhacky@test.com", LocalDate.of(2025, 12, 12), LocalTime.of(12, 12), 4, Set.of("SMS"));

        when(reservationService.createReservation(Mockito.any(ReservationDto.class))).thenReturn(createdReservation);

        // Act & Assert
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdReservation)));
    }
    //endregion

    //region PUT Test
    @Test
    public void updateReservation_ShouldReturnUpdatedReservation() throws Exception {
        // Arrange
        ReservationDto updatedReservation = new ReservationDto("Zhack Alawi", "1234567890", "zhacky@test.com", LocalDate.now(), LocalTime.now(), 5, Set.of("Email"));
        when(reservationService.updateReservation(Mockito.eq(1L), Mockito.any(ReservationDto.class)))
                .thenReturn(Optional.of(updatedReservation));

        // Act & Assert
        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedReservation)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedReservation)));
    }

    @Test
    public void updateReservation_ShouldReturnNotFound_WhenReservationDoesNotExist() throws Exception {
        when(reservationService.updateReservation(Mockito.eq(1L), Mockito.any(ReservationDto.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ReservationDto())))
                .andExpect(status().isNotFound());
    }

    //endregion

    //region DELETE Test
    @Test
    public void deleteReservation_ShouldReturnNoContent_WhenSuccessful() throws Exception {
        when(reservationService.deleteReservation(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteReservation_ShouldReturnNotFound_WhenReservationDoesNotExist() throws Exception {
        when(reservationService.deleteReservation(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //endregion
}
