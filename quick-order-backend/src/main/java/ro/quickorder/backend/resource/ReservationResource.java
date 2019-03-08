package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.services.ReservationService;

import java.util.List;


@RestController
@RequestMapping(value = "/reservation")
public class ReservationResource {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return reservationRepository.findById(id).toString();
    }

    @RequestMapping(path = "/unconfirmed", method = RequestMethod.GET)
    public List<ReservationDto> getAllUnconfirmed(){
        return reservationService.getAllUnconfirmed();
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.POST)
    public void confirmReservation(ReservationDto reservationDto, List<TableFoodDto> tableFoodDtos){
        reservationService.confirmReservation(reservationDto, tableFoodDtos);
    }



}
