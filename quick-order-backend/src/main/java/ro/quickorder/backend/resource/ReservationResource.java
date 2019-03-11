package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.repository.MenuItemRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.service.ReservationService;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import ro.quickorder.backend.services.ReservationService;

import javax.validation.constraints.NotNull;
import java.util.List;


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


    @RequestMapping(path = "/unconfirmed", method = RequestMethod.GET)
    public List<ReservationDto> getAllUnconfirmed(){
        return reservationService.getAllUnconfirmed();
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public void confirmReservation(@NotNull ReservationDto reservationDto,@NotNull List<TableFoodDto> tableFoodDtos){
        reservationService.confirmReservation(reservationDto, tableFoodDtos);
    }



}
