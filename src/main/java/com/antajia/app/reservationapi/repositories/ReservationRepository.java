package com.antajia.app.reservationapi.repositories;

import com.antajia.app.reservationapi.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //region Custom Queries
    @Query("SELECT DISTINCT r FROM Reservation r LEFT JOIN FETCH r.contactMethods")
    List<Reservation> findAllWithContactMethods();

    @Query("SELECT r FROM Reservation r LEFT JOIN FETCH r.contactMethods WHERE r.id = :id")
    Reservation findByIdWithContactMethods(Long id);
    //endregion
}
