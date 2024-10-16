package com.antajia.app.reservationapi.utils;

import com.antajia.app.reservationapi.models.Reservation;
import com.antajia.app.reservationapi.models.ContactMethod;
import com.antajia.app.reservationapi.repositories.ReservationRepository;
import com.antajia.app.reservationapi.repositories.ContactMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.HashSet;

@Configuration
public class Seeder implements CommandLineRunner {

    private final ReservationRepository reservationRepository;
    private final ContactMethodRepository contactMethodRepository;

    @Autowired
    public Seeder(ReservationRepository reservationRepository, ContactMethodRepository contactMethodRepository) {
        this.reservationRepository = reservationRepository;
        this.contactMethodRepository = contactMethodRepository;
    }

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        if (contactMethodRepository.count() == 0 && reservationRepository.count() == 0) {
            ContactMethod email = new ContactMethod("Email");
            ContactMethod sms = new ContactMethod("SMS");
            ContactMethod phone = new ContactMethod("Phone");

            contactMethodRepository.saveAll(Set.of(email, sms, phone));

            Set<ContactMethod> emailMethod = new HashSet<>(Set.of(email));
            Set<ContactMethod> smsMethod = new HashSet<>(Set.of(sms));
            Set<ContactMethod> phoneMethod = new HashSet<>(Set.of(phone));

            Reservation reservation1 = new Reservation( "Zhack Ariya", "+639234567890", "zhackariya@test.com",
                    LocalDate.of(2024, 5, 15), LocalTime.of(18, 30), 2, emailMethod);
            Reservation reservation2 = new Reservation("Aladdin Alawi", "+639187654321", "a.alawi@test.com",
                    LocalDate.of(2024, 5, 16), LocalTime.of(19, 0), 4, smsMethod);
            Reservation reservation3 = new Reservation("Zhack Alawi", "+9122334455", "zhack.alawi@test.com",
                    LocalDate.of(2024, 5, 17), LocalTime.of(20, 0), 6, phoneMethod);

            reservationRepository.saveAll(Set.of(reservation1, reservation2, reservation3));

            System.out.println("Seeded 3 contact methods and 3 reservations.");
        } else {
            System.out.println("Database already contains data.");
        }
    }
}
