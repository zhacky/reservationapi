package com.antajia.app.reservationapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "reservation_time")
    private LocalTime reservationTime;

    @Column(name = "number_of_guests")
    private Integer numberOfGuests;

    @ManyToMany
    @JoinTable(name = "reservation_contact_methods",
               joinColumns = @JoinColumn(name = "reservation_id"),
               inverseJoinColumns = @JoinColumn(name = "contact_method_id"))
    private Set<ContactMethod> contactMethods;

    public Reservation(String name, String phoneNumber, String email, LocalDate reservationDate, LocalTime reservationTime, Integer numberOfGuests, Set<ContactMethod> contactMethods) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.numberOfGuests = numberOfGuests;
        this.contactMethods = contactMethods;
    }
}
