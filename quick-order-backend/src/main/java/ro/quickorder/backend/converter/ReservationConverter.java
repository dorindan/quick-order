package ro.quickorder.backend.converter;

import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ReservationDto;

public class ReservationConverter {
    public static ReservationDto toReservationDto(Reservation reservation){
        if(reservation==null){
            return null;
        }
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCheckInTime(reservation.getCheckInTime());
        reservationDto.setCheckOutTime(reservation.getCheckOutTime());
        reservationDto.setConfirmed(reservation.isConfirmed());
        reservationDto.setNumberOfPersons(reservation.getNumberOfPersons());
        reservationDto.setStatus(reservation.getStatus());

        return reservationDto;
    }
    public static Reservation toReservation (ReservationDto reservationDto){
        if(reservationDto==null){
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setCheckInTime(reservationDto.getCheckInTime());
        reservation.setCheckOutTime(reservationDto.getCheckOutTime());
        reservation.setConfirmed(reservationDto.getConfirmed());
        reservation.setStatus(reservationDto.getStatus());
        reservation.setNumberOfPersons(reservationDto.getNumberOfPersons());
        return reservation;
    }
}
