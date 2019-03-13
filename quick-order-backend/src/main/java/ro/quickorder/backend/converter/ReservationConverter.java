package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ReservationDto;

@Component
public class ReservationConverter {

    public Reservation convertReservationDtoToReservation(ReservationDto reservationDto){
        Reservation reservation= new Reservation();
        reservation.setCheckInTime(reservationDto.getCheckInTime());
        reservation.setCheckOutTime(reservationDto.getCheckOutTime());
        reservation.setStatus(reservationDto.getStatus());
        reservation.setConfirmed(reservationDto.isConfirmed());
        reservation.setNumberOfPersons(reservationDto.getNumberOfPersons());
        reservation.setReservationName(reservationDto.getReservationName());
        return reservation;
    }

    public ReservationDto convertReservationToReservationDto(Reservation reservation){
        ReservationDto reservationDto= new ReservationDto();
        reservationDto.setCheckInTime(reservation.getCheckInTime());
        reservationDto.setCheckOutTime(reservation.getCheckOutTime());
        reservationDto.setStatus(reservation.getStatus());
        reservationDto.setConfirmed(reservation.isConfirmed());
        reservationDto.setNumberOfPersons(reservation.getNumberOfPersons());
        reservationDto.setReservationName(reservation.getReservationName());
        return reservationDto;
    }



}
