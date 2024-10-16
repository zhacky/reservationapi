package com.antajia.app.reservationapi.repositories;

import com.antajia.app.reservationapi.models.ContactMethod;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMethodRepository extends JpaRepository<ContactMethod, Long> {
    Set<ContactMethod> findAllByNameIn(Set<String> names);
}
