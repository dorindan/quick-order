package ro.quickorder.backend.converter;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ConfirmReservationDto;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts Reservations to their corresponding DTO and vice versa.
 *
 * @author R. Lupoaie
 */

@Component
public class ReservationConverter {

    @Autowired
    private TableFoodConverter tableFoodConverter;
    @Autowired
    private UserConverter userConverter;

    public ReservationConverter() {

    }

    public ReservationConverter(TableFoodConverter tableFoodConverter, UserConverter userConverter) {
        this.tableFoodConverter = tableFoodConverter;
        this.userConverter = userConverter;
    }

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
        if (reservationDto.getUser() != null) {
            reservation.setUser(userConverter.toUser(reservationDto.getUser()));
        }
        if (reservationDto.getTableFoodDtos() != null) {
            reservation.setTables(reservationDto.getTableFoodDtos().stream()
                    .map(tableFoodDto -> tableFoodConverter.toTableFood(tableFoodDto)).collect(Collectors.toList()));
        }
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
        if (reservation.getUser() != null) {
            reservationDto.setUser(userConverter.toUserDto(reservation.getUser()));
        }
        if (reservation.getTables() != null && Hibernate.isInitialized(reservation.getTables())) {
            reservationDto.setTableFoodDtos(reservation.getTables().stream()
                    .map(tableFood -> tableFoodConverter.toTableFoodDto(tableFood)).collect(Collectors.toList()));
        }
        return reservationDto;
    }

    public ReservationDto toReservationDtoFromConfirmReservationDto(ConfirmReservationDto confirmReservationDto) {
        if (confirmReservationDto == null) {
            return null;
        }
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setCheckInTime(confirmReservationDto.getCheckInTime());
        reservationDto.setCheckOutTime(confirmReservationDto.getCheckOutTime());
        reservationDto.setStatus(confirmReservationDto.getStatus());
        reservationDto.setConfirmed(confirmReservationDto.isConfirmed());
        reservationDto.setNumberOfPersons(confirmReservationDto.getNumberOfPersons());
        reservationDto.setReservationName(confirmReservationDto.getReservationName());
        reservationDto.setUser(confirmReservationDto.getUser());
        return reservationDto;
    }


    public ConfirmReservationDto toConfirmReservationDtoFromReservationDto(ReservationDto reservationDto, List<TableFoodDto> tableFoodDtos) {
        if (reservationDto == null) {
            return null;
        }
        ConfirmReservationDto confirmReservationDto = new ConfirmReservationDto();
        confirmReservationDto.setCheckInTime(reservationDto.getCheckInTime());
        confirmReservationDto.setCheckOutTime(reservationDto.getCheckOutTime());
        confirmReservationDto.setStatus(reservationDto.getStatus());
        confirmReservationDto.setConfirmed(reservationDto.isConfirmed());
        confirmReservationDto.setNumberOfPersons(reservationDto.getNumberOfPersons());
        confirmReservationDto.setReservationName(reservationDto.getReservationName());
        reservationDto.setUser(reservationDto.getUser());
        confirmReservationDto.setTableFoodDtos(tableFoodDtos);
        return confirmReservationDto;
    }

}
