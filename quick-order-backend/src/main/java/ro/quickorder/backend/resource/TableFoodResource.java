package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.sql.Timestamp;
import java.util.List;
import ro.quickorder.backend.service.TableFoodService;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping(value = "/table")
public class TableFoodResource {

    @Autowired
    TableFoodService tableFoodService;

    @RequestMapping(path = "/free", method = RequestMethod.GET)
    public List<TableFoodDto> getAllFree(Timestamp checkInTime, Timestamp checkOutTime){
        return tableFoodService.getAllFree(checkInTime,checkOutTime);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<TableFoodDto> getAllTables(){
        return tableFoodService.getAll();
    }

}
