package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.service.ReservationService;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/reservation")
public class ReservationResource {

    @Autowired
    ReservationService reservationService;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addReservation(@RequestBody ReservationDto reservation) {
        reservationService.addReservation(reservation);
    }

    @RequestMapping(path = "/addConfirmed", method = RequestMethod.POST)
    public void addReservationConfirmed(@RequestBody @NotNull ReservationDto reservationDto){
        reservationService.addReservationConfirmed(reservationDto);
    }


    @RequestMapping(path = "/unconfirmed", method = RequestMethod.GET)
    public List<ReservationDto> getAllUnconfirmed(){
        return reservationService.getAllUnconfirmed();
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public void confirmReservation(@NotNull ReservationDto reservationDto,@NotNull List<TableFoodDto> tableFoodDtos){
        reservationService.confirmReservation(reservationDto, tableFoodDtos);
    }

    @RequestMapping(path = "/reservationsForTable/{tableNr}", method = RequestMethod.GET)
    public List<ReservationDto> getReservationsForTableByTableNumber (@PathVariable Integer tableNr){
        return reservationService.getReservationsForTableByTableNumber (tableNr);
    }
}
