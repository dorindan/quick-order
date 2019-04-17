package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import ro.quickorder.backend.model.dto.ConfirmReservationDto;
=======
import ro.quickorder.backend.model.Reservation;
>>>>>>> 3c8d06762cb6dd22218259b91b18bef60e228ae9
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.service.ReservationService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/reservation")
public class ReservationResource {
<<<<<<< HEAD
    @Autowired
    ReservationRepository reservationRepository;
=======

>>>>>>> 3c8d06762cb6dd22218259b91b18bef60e228ae9
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

    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public void confirmReservation(@RequestBody ConfirmReservationDto confirmReservationDto) {
        reservationService.confirmReservation(new ReservationDto(confirmReservationDto.getCheckInTime(), confirmReservationDto.getCheckOutTime(), confirmReservationDto.getStatus(), confirmReservationDto.isConfirmed(), confirmReservationDto.getNumberOfPersons(), confirmReservationDto.getReservationName()), confirmReservationDto.getTableFoodListDto());
    }
<<<<<<< HEAD
=======

    @RequestMapping(path = "/reservationsForTable/{tableNr}", method = RequestMethod.GET)
    public List<ReservationDto> getReservationsForTableByTableNumber (@PathVariable Integer tableNr){
        return reservationService.getReservationsForTableByTableNumber (tableNr);
    }



>>>>>>> 3c8d06762cb6dd22218259b91b18bef60e228ae9
}
