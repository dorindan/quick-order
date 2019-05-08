package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.ConfirmReservationDto;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.service.ReservationService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/reservation")
public class ReservationResource {
    @Autowired
    ReservationService reservationService;

    @RequestMapping(method = RequestMethod.POST)
    public void addReservation(@RequestBody ReservationDto reservation) {
        reservationService.addReservation(reservation);
    }

    @RequestMapping(path = "/unconfirmed", method = RequestMethod.GET)
    public List<ReservationDto> getAllUnconfirmed() {
        return reservationService.getAllUnconfirmed();
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.PUT)
    public void confirmReservation(@RequestBody ConfirmReservationDto confirmReservationDto) {
        reservationService.confirmReservation(confirmReservationDto);
    }

    @RequestMapping(path = "/reservationsForTable/{tableNr}", method = RequestMethod.GET)
    public List<ReservationDto> getReservationsForTableByTableNumber(@PathVariable Integer tableNr) {
        return reservationService.getReservationsForTableByTableNumber(tableNr);
    }
}
