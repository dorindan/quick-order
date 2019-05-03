package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.service.CustomDateDeserializer;
import ro.quickorder.backend.service.TableFoodService;

import java.util.List;


@RestController
@RequestMapping(value = "/table")
public class TableFoodResource {
    @Autowired
    TableFoodService tableFoodService;

    @RequestMapping(path = "/free/{checkInTime}/{checkOutTime}", method = RequestMethod.GET)
    public List<TableFoodDto> getAllFree(@PathVariable String checkInTime, @PathVariable String checkOutTime) {
        return tableFoodService.getAllFree(CustomDateDeserializer.deserialize(checkInTime),
                CustomDateDeserializer.deserialize(checkOutTime));
    }

    @RequestMapping(path = "/free/{reservationName}", method = RequestMethod.GET)
    public List<TableFoodDto> getAllAssignedTablesOfAReservation(@PathVariable String reservationName) {
        return tableFoodService.getAllAssignedTablesOfAReservation(reservationName);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<TableFoodDto> getAllTables() {
        return tableFoodService.getAll();
    }
}
