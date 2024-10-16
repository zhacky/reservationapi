package com.antajia.app.reservationapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/*
 * DTOs (Data Transfer Objects) are used to selectively expose specific fields of a model/entity.
 * This helps control the data being transferred between different layers of the application,
 * ensuring that only necessary information is exposed, while sensitive or irrelevant fields
 * are kept hidden. In this example, we are utilizing all the fields, but in the future development,
 * we should be selective of the fields to expose.
 */

/**
 * Data Transfer Object (DTO) for transferring reservation details between data models and the reservation controller.
 */
@Data
public class ReservationDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("reservation_date")
    private LocalDate reservationDate;
    @JsonProperty("reservation_time")
    private LocalTime reservationTime;
    @JsonProperty("number_of_guests")
    private Integer numberOfGuests;
    @JsonProperty("contact_methods")
    private Set<String> contactMethods;

    public ReservationDto(String name, String phoneNumber, String email, LocalDate reservationDate, LocalTime reservationTime, Integer numberOfGuests, Set<String> contactMethods) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.numberOfGuests = numberOfGuests;
        this.contactMethods = contactMethods;
    }

    public ReservationDto() {
    }
    // Constructor, getters, and setters are automatically generated by Lombok
    // You can add custom methods here if needed

}
