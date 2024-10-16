package com.antajia.app.reservationapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "contact_methods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "contactMethods")
    private Set<Reservation> reservations;

    public ContactMethod(String name) {
        this.name = name;
    }
}
