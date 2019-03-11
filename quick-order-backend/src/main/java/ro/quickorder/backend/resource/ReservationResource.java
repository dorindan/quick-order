package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.repository.MenuItemRepository;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.service.ReservationService;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@CrossOrigin
@RequestMapping(value = "/reservation")
public class ReservationResource {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return reservationRepository.findById(id).toString();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addReservation(@RequestBody ReservationDto reservation) throws ParseException {
        reservationService.addReservation(reservation);
    }

}
