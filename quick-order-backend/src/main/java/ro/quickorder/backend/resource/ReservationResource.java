package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.ConfirmReservationDto;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reservation")
public class ReservationResource {
    @Autowired
    ReservationService reservationService;

    @RequestMapping(method = RequestMethod.POST)
    public void addReservation(@RequestBody ReservationDto reservation) {
        reservationService.addReservation(reservation);
    }

    @RequestMapping(path = "/unconfirmed", method = RequestMethod.GET)
    @PreAuthorize("hasRole('WAITER')")
    public List<ReservationDto> getAllUnconfirmed() {
        return reservationService.getAllReservationUnconfirmed();
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('USER') or hasRole('WAITER')")
    public void confirmReservation(@RequestBody ConfirmReservationDto confirmReservationDto) {
        reservationService.confirmReservation(confirmReservationDto);
    }

    @RequestMapping(path = "/reservationsForTable/{tableNr}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('WAITER')")
    public List<ReservationDto> getReservationsForTableByTableNumber(@PathVariable Integer tableNr) {
        return reservationService.getReservationsForTableByTableNumber(tableNr);
    }

    @RequestMapping(path = "/confirmed/{reservationName}", method = RequestMethod.GET)
    public boolean reservationConfirmed(@PathVariable String reservationName) {
        return reservationService.reservationConfirmed(reservationName);
    }

    @RequestMapping(path = "/actual-user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<ReservationDto> reservationOfActualUser() {
        return reservationService.reservationOfActualUser();
    }

    @RequestMapping(path = "/remove/{reservationName}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('USER')")
    public void removeReservation(@PathVariable String reservationName) {
        reservationService.removeReservation(reservationName);
    }

}
