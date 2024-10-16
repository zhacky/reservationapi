package com.antajia.app.reservationapi.services;

import com.antajia.app.reservationapi.models.Reservation;
import com.antajia.app.reservationapi.models.ContactMethod;
import com.antajia.app.reservationapi.dtos.ReservationDto;
import com.antajia.app.reservationapi.repositories.ReservationRepository;
import com.antajia.app.reservationapi.repositories.ContactMethodRepository;
import com.antajia.app.reservationapi.services.base.BaseReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

@Service
public class ReservationService implements BaseReservationService {

    private final ReservationRepository reservationRepository;
    private final ContactMethodRepository contactMethodRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ContactMethodRepository contactMethodRepository) {
        this.reservationRepository = reservationRepository;
        this.contactMethodRepository = contactMethodRepository;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        List<ReservationDto> reservationDtos = reservationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return reservationDtos;
    }
    @Override
    public Optional<ReservationDto> getReservationById(Long id) {
        Optional<ReservationDto> reservationDto = reservationRepository.findById(id).map(this::convertToDto);
        return reservationDto;
    }
    @Override
    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = convertToEntity(reservationDto);
        Reservation savedReservation = reservationRepository.save(reservation);
        ReservationDto resDto = convertToDto(savedReservation);
        return resDto;
    }
    @Override
    public Optional<ReservationDto> updateReservation(Long id, ReservationDto updatedReservationDto) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    updateReservationFromDto(reservation, updatedReservationDto);
                    return convertToDto(reservationRepository.save(reservation));
                });
    }
    @Override
    public boolean deleteReservation(Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservationRepository.delete(reservation);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public ReservationDto convertToDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setName(reservation.getName());
        dto.setPhoneNumber(reservation.getPhoneNumber());
        dto.setEmail(reservation.getEmail());
        dto.setReservationDate(reservation.getReservationDate());
        dto.setReservationTime(reservation.getReservationTime());
        dto.setNumberOfGuests(reservation.getNumberOfGuests());
        dto.setContactMethods(reservation.getContactMethods().stream()
                .map(ContactMethod::getName)
                .collect(Collectors.toSet()));
        return dto;
    }

    @Override
    public Reservation convertToEntity(ReservationDto dto) {
        Reservation reservation = new Reservation();
        updateReservationFromDto(reservation, dto);
        return reservation;
    }

    private void updateReservationFromDto(Reservation reservation, ReservationDto dto) {
        reservation.setName(dto.getName());
        reservation.setPhoneNumber(dto.getPhoneNumber());
        reservation.setEmail(dto.getEmail());
        reservation.setReservationDate(dto.getReservationDate());
        reservation.setReservationTime(dto.getReservationTime());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        Set<ContactMethod> contactMethods = new HashSet<>(contactMethodRepository.findAllByNameIn(dto.getContactMethods()));
        reservation.setContactMethods(contactMethods);
    }
}
