package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ReservationDto;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link ReservationConverter}
 *
 * @author R. Lupoaie
 */
public class ReservationConverterTest {

    private ReservationConverter reservationConverter = new ReservationConverter();

    @Test
    public void testConvertReservationToDto() {
        Reservation reservation = new Reservation(new Timestamp(10), new Timestamp(20), null, null,
                5, false, "status", null);
        ReservationDto reservationDto = reservationConverter.toReservationDto(reservation);
        assertEquals(reservation.getCheckInTime(), reservationDto.getCheckInTime());
        assertEquals(reservation.getCheckOutTime(), reservationDto.getCheckOutTime());
        assertEquals(reservation.getNumberOfPersons(), reservationDto.getNumberOfPersons());
        assertEquals(reservation.getReservationName(), reservationDto.getReservationName());
        assertEquals(reservation.getStatus(), reservationDto.getStatus());
        assertEquals(reservation.isConfirmed(), reservationDto.isConfirmed());
    }

    @Test
    public void testConvertReservationToDtoWhenReservationIsNull() {
        ReservationDto reservationDto = reservationConverter.toReservationDto(null);
        assertNull(reservationDto);
    }

    @Test
    public void testConvertDtoToReservation() {
        ReservationDto reservationDto = new ReservationDto(new Timestamp(10), new Timestamp(20), "status",
                false, 3, "name");
        Reservation reservation = reservationConverter.toReservation(reservationDto);
        assertEquals(reservationDto.getCheckInTime(), reservation.getCheckInTime());
        assertEquals(reservationDto.getCheckOutTime(), reservation.getCheckOutTime());
        assertEquals(reservationDto.getNumberOfPersons(), reservation.getNumberOfPersons());
        assertEquals(reservationDto.getReservationName(), reservation.getReservationName());
        assertEquals(reservationDto.getStatus(), reservation.getStatus());
        assertEquals(reservationDto.isConfirmed(), reservation.isConfirmed());
        assertNull(reservation.getCommand());
        assertNull(reservation.getTables());
        assertNull(reservation.getUser());
    }

    @Test
    public void testConvertDtoToReservationWhenDtoIsNull() {
        Reservation reservation = reservationConverter.toReservation(null);
        assertNull(reservation);
    }


}