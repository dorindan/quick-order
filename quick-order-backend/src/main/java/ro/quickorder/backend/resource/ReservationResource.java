package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.repository.MenuItemRepository;
import ro.quickorder.backend.repository.ReservationRepository;


@RestController
public class ReservationResource {
    @Autowired
    ReservationRepository reservationRepository;

    @RequestMapping(path = "/reservation", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return reservationRepository.findById(id).toString();
    }
}
