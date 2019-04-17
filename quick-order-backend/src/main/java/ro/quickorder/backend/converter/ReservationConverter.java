package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ReservationDto;

/**
 *  Converts Reservations to their corresponding DTO and vice versa.
 * @author R. Lupoaie
 */

@Component
public class ReservationConverter {
    public Reservation toReservation(ReservationDto reservationDto) {
        if (reservationDto == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setCheckInTime(reservationDto.getCheckInTime());
        reservation.setCheckOutTime(reservationDto.getCheckOutTime());
        reservation.setStatus(reservationDto.getStatus());
        reservation.setConfirmed(reservationDto.isConfirmed());
        reservation.setNumberOfPersons(reservationDto.getNumberOfPersons());
        reservation.setReservationName(reservationDto.getReservationName());
        return reservation;
    }

    public ReservationDto toReservationDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCheckInTime(reservation.getCheckInTime());
        reservationDto.setCheckOutTime(reservation.getCheckOutTime());
        reservationDto.setStatus(reservation.getStatus());
        reservationDto.setConfirmed(reservation.isConfirmed());
        reservationDto.setNumberOfPersons(reservation.getNumberOfPersons());
        reservationDto.setReservationName(reservation.getReservationName());
        return reservationDto;
    }
}
